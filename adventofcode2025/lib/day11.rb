# Dev
class Dev
  attr_accessor :name, :outs, :ins, :visited, :paths, :dac, :fft, :none, :both
  def initialize(name)
    @name = name
    @outs = Hash.new
    @ins = Hash.new
    @visited = false
    @paths = 0
    @dac = 0
    @fft = 0
    @none = 0
    @both = 0
  end
  def to_s
    "#{@name} (dac=#{@dac}, fft=#{@fft}, both=#{@both}, none=#{@none})"
  end
end


module Day11
  
  
  def self.parse_input(lines)
    devs = Hash.new
    for line in lines
      parts = line.split(": ")
      if devs.key?(parts[0])
        dev = devs[parts[0]]
      else
        dev = Dev.new(parts[0])
        devs[dev.name] = dev
      end
      for o in parts[1].split(" ")
        if devs.key?(o)
          odev = devs[o]
        else
          odev = Dev.new(o)
          devs[odev.name] = odev
        end
        dev.outs[odev.name] = odev
        # Reverse relationship
        odev.ins[dev.name] = dev
      end
    end
    return devs
  end

  def self.count_paths_dfs(dev)
    # Assume DAG - no cycles
    # Base case
    if dev.name == "out"
      return 1
    end
    paths = 0
    for o in dev.outs.values
      paths += self.count_paths_dfs(o)
    end
    return paths
  end

  def self.count_paths_inverse_bfs(finish)
    queue = [finish]
    finish.visited = true
    finish.paths = 1
    while !queue.empty?
      dev = queue.shift
      # All input nodes
      for dev2 in dev.ins.values
        if !queue.include?(dev2)
          queue.push(dev2)
        end
      end
      if dev.name == "out"
        next
      end
      # add path if only all inputs are confirmed
      if !dev.outs.empty? && dev.outs.values.all?{|o| o.visited }
        dev.paths = dev.outs.values.map{|o| o.paths }.sum
        dev.visited = true
      else
        # otherwise put at the end of the queue
        queue.push(dev)
      end
    end
  end

  def self.part1(lines)
    devs = self.parse_input(lines)
    self.count_paths_inverse_bfs(devs["out"])
    return devs["you"].paths
  end

  def self.count_dac_fft_inverse_bfs(finish)
    queue = [finish]
    finish.visited = true
    finish.none = 1
    while !queue.empty?
      dev = queue.shift
      # All input nodes
      for dev2 in dev.ins.values
        if !queue.include?(dev2)
          queue.push(dev2)
        end
      end
      if dev.name == "out"
        next
      end
      # add path if only all inputs are confirmed
      if !dev.outs.empty? && dev.outs.values.all?{|o| o.visited }
        if dev.name != "dac" && dev.name != "fft"
          # sum nones, only dac, only fft, and both
          dev.none = dev.outs.values.map{|o| o.none }.sum
          dev.dac = dev.outs.values.map{|o| o.dac }.sum
          dev.fft = dev.outs.values.map{|o| o.fft }.sum
          dev.both = dev.outs.values.map{|o| o.both }.sum
        elsif dev.name == "fft"
          # All the paths that have none, now they have at least fft
          dev.fft = dev.outs.values.map{|o| o.none }.sum
          # All the paths that already had fft only, keep having it
          dev.fft += dev.outs.values.map{|o| o.fft }.sum
          # All the paths that already had dac only, now have both
          dev.both = dev.outs.values.map{|o| o.dac }.sum
          # All the paths that had both, keep having it
          dev.both += dev.outs.values.map{|o| o.both }.sum
          # There are no paths that have only dac now / nor paths that have none
        elsif dev.name == "dac"
          # Same thing - the other way arround
          dev.dac = dev.outs.values.map{|o| o.none }.sum
          dev.dac += dev.outs.values.map{|o| o.dac }.sum
          dev.both = dev.outs.values.map{|o| o.fft }.sum
          dev.both += dev.outs.values.map{|o| o.both }.sum
          # There are no paths that have only fft now / nor paths that have none
        end
        dev.visited = true
      else
        # otherwise put at the end of the queue
        queue.push(dev)
      end
    end
  end
  
  def self.part2(lines)
    devs = self.parse_input(lines)
    self.count_dac_fft_inverse_bfs(devs["out"])
    return devs["svr"].both
  end
end

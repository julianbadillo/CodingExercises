import java.util.Arrays;

public class PeopleTower {

	int getMaxPeopleTower(Person[] people){
		Arrays.sort(people, (p1, p2) -> (p1.height != p2.height? p2.height-p1.height: p2.weight - p1.weight));
		int n = people.length;
		if(n == 0) return 0;
		int [] maxPa = new int[n];
		for (int i = 1; i < n; i++) {
			int pa = 0;
			for (int j = 0; j < i; j++) {
				if(people[j].height > people[i].height && 
						people[j].weight > people[i].weight &&
						pa < maxPa[j] + 1)
					pa = maxPa[j] + 1;
			}
			maxPa[i] = pa;
		}
		return Arrays.stream(maxPa).max().getAsInt() + 1;
	}
	
}

class Person{
	int height;
	int weight;
	public Person(int height, int weight) {
		this.height = height;
		this.weight = weight;
	}	
}
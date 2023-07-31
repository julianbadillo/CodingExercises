from itertools import chain

class SortedSet:
    """Maintain a set of items in such a way that iter(set) returns the items in sorted order.
    We also allow a custom comparison routine, and augment the usual add() and remove() methods
    with an update() method that tells the set that a single item's position in the order might
    have changed."""
    
    def __init__(self,iterable=[],comparison=None):
        """Create a new sorted set with the given comparison function."""
        self._comparison = comparison
        self._set = set(iterable)
        self._previous = None

    def __len__(self):
        """How many items do we have?"""
        return len(self._set)

    def add(self,item):
        """Add the given item to a sorted set."""
        self._set.add(item)
        if self._previous:
            self._additions.add(item)

    def remove(self,item):
        """Remove the given item from a sorted set."""
        self._set.remove(item)
        if self._previous:
            self._removals.add(item)
            self._additions.discard(item)

    def update(self,item):
        """Flag the given item as needing re-comparison with its neighbors in the order."""
        if self._previous:
            self._removals.add(item)
            self._additions.add(item)

    def __iter__(self):
        if not self._previous:
            self._previous = sorted(self._set,key=self._comparison)
        else:
            self._previous = sorted(chain(self._additions,
                                          (x for x in self._previous if x not in self._removals)),
                                    key=self._comparison)
        self._removals = set()
        self._additions = set()
        return iter(self._previous)        
    
    def __repr__(self):
        return str([s for s in self])
        
        
class SortablePerson:
    def __init__(self, name, lastname, id):
        self.name = name
        self.lastname = lastname
        self.id = id
        
    def __repr__(self):
        return self.name + ' '+self.lastname + ' ('+str(self.id)+')'
    
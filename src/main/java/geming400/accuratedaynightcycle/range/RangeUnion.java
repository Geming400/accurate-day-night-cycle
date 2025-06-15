package geming400.accuratedaynightcycle.range;

import org.jetbrains.annotations.Nullable;

import java.util.Vector;

@SuppressWarnings("unused")
public class RangeUnion {
    private Vector<Range> ranges;

    public RangeUnion() {}
    public RangeUnion(Vector<Range> ranges) { this.ranges = ranges; }

    /**
     * Will check if the param {@code number} is contained between {@link Range#getMin()} and {@link Range#getMax()} from all ranges contained in this {@link RangeUnion}
     *
     * @param number the number to check if it's contained in all {@link Range}'s ranges
     */
    public boolean contains(int number) {
        for (Range _range : this.ranges) {
            if (_range.contains(number)) { return true; }
        }

        return false;
    }

    /**
     * Will check if the param {@code range} is contained between {@link Range#getMin()} and {@link Range#getMax()} from all ranges contained in this {@link RangeUnion}
     *
     * @param range the range to check if it's contained in all {@link Range}'s ranges
     */
    public boolean contains(Range range) {
        for (Range _range : this.ranges) {
            if (_range.contains(range)) { return true; }
        }

        return false;
    }

    /**
     * Will check if the param {@code number} is in {@link Range#min} and {@link Range#max} from all ranges contained in this {@link RangeUnion} (exclusive)
     *
     * @param number the number to check if it's in all {@link Range}'s ranges
     */
    public boolean in(int number) {
        for (Range _range : this.ranges) {
            if (_range.in(number)) { return true; }
        }

        return false;
    }

    /**
     * Will check if the param {@code range} is in {@link Range#min} and {@link Range#max} from all ranges contained in this {@link RangeUnion} (exclusive)
     *
     * @param range the range to check if it's in all {@link Range}'s ranges
     */
    public boolean in(Range range) {
        for (Range _range : this.ranges) {
            if (_range.in(range)) { return true; }
        }

        return false;
    }

    /**
     * Adds a {@link Range} to a {@link Vector}
     @param range the {@link Range} to add to the {@link Vector}
     */
    public void add(Range range) { ranges.add(range); }

    /**
     * Remove a {@link Range} object from the {@link Vector}
     * @param range the {@link Range} object to remove from the {@link Vector}
     */
    public void remove(Range range) { ranges.remove(range); }

    /**
     * Remove a {@link Range} object from the {@link Vector} given by an index
     * @param index the index used to remove the {@link Range} object
     */
    public void remove(int index) { ranges.remove(index); }

    /**
     * @return a {@link Vector} containing a list of {@link Range} objects
     */
    public Vector<Range> getRanges() { return ranges; }

    /**
     * @return get a {@link Range} at a given index
     */
    public Range getRange(int index) { return ranges.get(index); }

    /**
     * @return The minimum value of all {@link Range}s objects
     */
    @Nullable
    public Double getMin() {
        if (ranges.isEmpty()) { return null; }

        double min = ranges.firstElement().min;

        for (Range range : ranges) {
            if (range.min < min) {
                min = range.min;
            }
        }

        return min;
    }

    /**
     * @return The maximum value of all {@link Range}s objects
     */
    @Nullable
    public Double getMax() {
        if (ranges.isEmpty()) { return null; }

        double max = ranges.firstElement().max;

        for (Range range : ranges) {
            if (max > range.max) {
                max = range.max;
            }
        }

        return max;
    }

    /**
     * This function will return the 'full range' of that range union
     * @see RangeUnion#getMin()
     * @see RangeUnion#getMax()
     * @return The full range. Ex:
     * <pre>
     * {@code
     * RangeUnion union = new RangeUnion();
     * union.add(new Range(0, 10));
     * union.add(new Range(-12, 17));
     *
     * union.getFullRange(); // -> returns [-12, 17]
     * }
     * </pre>
     */
    @Nullable
    public Range getFullRange() {
        if (!ranges.isEmpty()) {
            return new Range(getMin(), getMax());
        }

        return null;
    }

    @Override
    public String toString() {
        Vector<String> rangesStrings = new Vector<>();
        
        return String.join(" u ", (CharSequence) ranges);
    }

    public boolean equals(RangeUnion other) {
        return other.getRanges() == ranges;
    }
}

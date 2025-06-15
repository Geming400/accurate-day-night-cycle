package geming400.accuratedaynightcycle.range;

import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public class Range {
    protected final double min;
    protected final double max;

    protected final boolean minExclusive;
    protected final boolean maxExclusive;

    /**
     * Creates a {@link Range} object.
     * If {@code min} is superior than {@code max}, it will switch them.

     @param min the minimum value
     @param minExclusive if the min value is exclusive (= {@code ]min, max]})
     @param max the maximum value
     @param maxExclusive if the min value is exclusive (= {@code [min, max[})
     */
    public Range(double min, boolean minExclusive, double max, boolean maxExclusive) {
        if (min == max) throw new AssertionError("'min' and 'max' arguments are equal");

        this.minExclusive = minExclusive;
        this.maxExclusive = maxExclusive;

        if (max > min) {
            this.min = min;
            this.max = max;
        } else {
            this.min = max;
            this.max = min;
        }
    }
    /**
     * Creates a {@link Range} object.
     * If {@code min} is superior than {@code max}, it will switch them.
     * It will create an inclusive range

     @param min the minimum value
     @param max the maximum value
     */
    public Range(double min, double max) {
        if (min == max) throw new AssertionError("'min' and 'max' arguments are equal");

        this.minExclusive = false;
        this.maxExclusive = false;

        if (max > min) {
            this.min = min;
            this.max = max;
        } else {
            this.min = max;
            this.max = min;
        }
    }

    // GETTERS

    public boolean isMinExclusive() { return minExclusive; }
    public boolean isMaxExclusive() { return maxExclusive; }

    public double getMin() { return min; }
    public double getMax() { return max; }



    protected boolean _contains(int number, boolean minExclusive, boolean maxExclusive) {
        boolean firstMember;
        boolean secondMember;

        if (minExclusive) { firstMember = number > min; } else { firstMember = number >= min; }
        if (maxExclusive) { secondMember = number < max; } else { secondMember = number <= max; }

        return (firstMember && secondMember);
    }

    protected boolean _rangeContains(Range range, boolean minExclusive, boolean maxExclusive) {
        boolean firstMember;
        boolean secondMember;

        if (minExclusive) { firstMember = range.getMin() > min; } else { firstMember = range.getMin() >= min; }
        if (maxExclusive) { secondMember = range.getMax() < max; } else { secondMember = range.getMax() <= max; }

        return (firstMember && secondMember);
    }

    /**
     * Will check if the param {@code number} is contained between {@link Range#getMin()} and {@link Range#getMax()}
     *
     * @param number the number to check if it's contained in this range
    */
    public boolean contains(int number) { return _contains(number, minExclusive, maxExclusive); }

    /**
     * Will check if the param {@code range} is contained between {@link Range#getMin()} and {@link Range#getMax()}
     *
     * @param range the range to check if it's contained in this range
     */
    public boolean contains(Range range) { return _rangeContains(range, minExclusive, maxExclusive); }

    /**
     * Will check if the param {@code number} is in {@link Range#getMin()} and {@link Range#getMax()} (exclusive)
     *
     * @param number the number to check if it's in this range
     */
    public boolean in(int number) { return _contains(number, true, true); }

    /**
     * Will check if the param {@code range} is in {@link Range#getMin()} and {@link Range#getMax()} (exclusive)
     *
     * @param range the range to check if it's in this range
     */
    public boolean in(Range range) { return _rangeContains(range, true, true); }

    @Override
    public String toString() {
        String firstChar;
        String lastChar;

        if (minExclusive) { firstChar = "]"; } else { firstChar = "["; }
        if (maxExclusive) { lastChar  = "["; } else { lastChar  = "]"; }
        return firstChar + min + ", " + max + lastChar;
    }

    public boolean equals(Range other) {
        return (
                other.getMin() == min
                && other.getMax() == max
                && other.isMinExclusive() == minExclusive
                && other.isMaxExclusive() == maxExclusive
        );
    }
}

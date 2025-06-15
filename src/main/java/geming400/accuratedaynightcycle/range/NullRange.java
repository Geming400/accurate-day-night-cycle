package geming400.accuratedaynightcycle.range;

public final class NullRange extends Range {
    /**
     * Creates a {@link NullRange} object.
     * Its purpose is to create a Range that isn't supposed to exist.
     * <p>
     * It will be inclusive, but it's minimum and maximum values will always be set to {@code 0} no matter what.
     */
    public NullRange() {
        super(0, 1); // dummy values
    }

    /**
     * @return Always return {@code 0d}
     */
    @Override
    public double getMin() {
        return 0d;
    }

    /**
     * @return Always return {@code 0d}
     */
    @Override
    public double getMax() {
        return 0d;
    }

    /**
     * @return Always return {@code false}
     */
    @Override
    protected boolean _contains(int number, boolean minExclusive, boolean maxExclusive) {
        return false;
    }

    /**
     * @return Always return {@code false}
     */
    @Override
    protected boolean _rangeContains(Range range, boolean minExclusive, boolean maxExclusive) {
        return false;
    }

    @Override
    public String toString() {
        return "[0, 0]";
    }
}

package geming400.accuratedaynightcycle.range;

import java.util.Vector;

public class RangeUnionBuilder {
    /**
     * Creates a {@link RangeUnionBuilder}. It is used to create a {@link RangeUnion} by the use of a builder
     */
    public RangeUnionBuilder() {};
    /**
     * Creates a {@link RangeUnionBuilder}. It is used to create a {@link RangeUnion} by the use of a builder
     * @param rangeUnion the rangeUnion that will be set by default.
     */
    private RangeUnionBuilder(RangeUnion rangeUnion) {
        this.rangeUnion = rangeUnion;
    };

    private RangeUnion rangeUnion = new RangeUnion();

    /**
     * Adds a {@link Range} to a {@link Vector}
     @param range the {@link Range} to add to the {@link Vector}
     */
    public RangeUnionBuilder add(Range range) {
        rangeUnion.add(range);
        return new RangeUnionBuilder(rangeUnion);
    }

    /**
     * Remove a {@link Range} object from a {@link Vector}
     * @param range the {@link Range} object to remove from the {@link Vector}
     */
    public RangeUnionBuilder remove(Range range) {
        rangeUnion.remove(range);
        return new RangeUnionBuilder(rangeUnion);
    }

    /**
     * @return get the final built {@link RangeUnion}
     */
    public RangeUnion get() {
        return rangeUnion;
    }
}

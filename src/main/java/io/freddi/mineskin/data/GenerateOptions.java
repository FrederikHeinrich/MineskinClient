package io.freddi.mineskin.data;

public class GenerateOptions {
    /**
     *  Automatically determined based on the image if not specified
     *  Options: CLASSIC, SLIM
     */
    public Variant variant;
    /**
     * The name of the skin on the profile
     */
    public String name;
    /**
     * The visibility of the skin on the profile. 0 = public, 1 = private
     */
    public int visibility;
}

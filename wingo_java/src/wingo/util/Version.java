//----------------------------------------------------------------------------
// The file Version.java is automatically created from Version.java.in.
// Do not edit Version.java.
//----------------------------------------------------------------------------

package wingo.util;

/** Version information for all packages. */
public final class Version
{
    /** Get version for packages in net.sf.gogui.
        @return Version string, no assumptions about the format should be
        made.
    */
    public static String get()
    {
        return "1.2pre2";
    }

    /** Make constructor unavailable; class is for namespace only. */
    private Version()
    {
    }
}

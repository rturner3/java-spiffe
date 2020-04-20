package spiffe.workloadapi;

import lombok.NonNull;
import lombok.Value;
import spiffe.bundle.x509bundle.X509BundleSet;
import spiffe.svid.x509svid.X509Svid;

import java.util.List;

/**
 * A <code>X509Context</code> represents the X509 materials that are fetched from the Workload API.
 * <p>
 * Contains a List of {@link X509Svid} and a {@link X509BundleSet}.
 */
@Value
public class X509Context {

    List<X509Svid> x509Svid;
    X509BundleSet x509BundleSet;

    public X509Context(@NonNull List<X509Svid> x509Svid, @NonNull X509BundleSet x509BundleSet) {
        this.x509Svid = x509Svid;
        this.x509BundleSet = x509BundleSet;
    }

    public X509Svid getDefaultSvid() {
        return x509Svid.get(0);
    }
}

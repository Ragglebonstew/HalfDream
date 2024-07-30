package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.MinimalExternalModuleDependency;
import org.gradle.plugin.use.PluginDependency;
import org.gradle.api.artifacts.ExternalModuleDependencyBundle;
import org.gradle.api.artifacts.MutableVersionConstraint;
import org.gradle.api.provider.Provider;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.internal.catalog.AbstractExternalDependencyFactory;
import org.gradle.api.internal.catalog.DefaultVersionCatalog;
import java.util.Map;
import org.gradle.api.internal.attributes.ImmutableAttributesFactory;
import org.gradle.api.internal.artifacts.dsl.CapabilityNotationParser;
import javax.inject.Inject;

/**
 * A catalog of dependencies accessible via the `libs` extension.
 */
@NonNullApi
public class LibrariesForLibs extends AbstractExternalDependencyFactory {

    private final AbstractExternalDependencyFactory owner = this;
    private final QuiltLibraryAccessors laccForQuiltLibraryAccessors = new QuiltLibraryAccessors(owner);
    private final QuiltedLibraryAccessors laccForQuiltedLibraryAccessors = new QuiltedLibraryAccessors(owner);
    private final VersionAccessors vaccForVersionAccessors = new VersionAccessors(providers, config);
    private final BundleAccessors baccForBundleAccessors = new BundleAccessors(objects, providers, config, attributesFactory, capabilityNotationParser);
    private final PluginAccessors paccForPluginAccessors = new PluginAccessors(providers, config);

    @Inject
    public LibrariesForLibs(DefaultVersionCatalog config, ProviderFactory providers, ObjectFactory objects, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) {
        super(config, providers, objects, attributesFactory, capabilityNotationParser);
    }

        /**
         * Creates a dependency provider for minecraft (com.mojang:minecraft)
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getMinecraft() {
            return create("minecraft");
    }

    /**
     * Returns the group of libraries at quilt
     */
    public QuiltLibraryAccessors getQuilt() {
        return laccForQuiltLibraryAccessors;
    }

    /**
     * Returns the group of libraries at quilted
     */
    public QuiltedLibraryAccessors getQuilted() {
        return laccForQuiltedLibraryAccessors;
    }

    /**
     * Returns the group of versions at versions
     */
    public VersionAccessors getVersions() {
        return vaccForVersionAccessors;
    }

    /**
     * Returns the group of bundles at bundles
     */
    public BundleAccessors getBundles() {
        return baccForBundleAccessors;
    }

    /**
     * Returns the group of plugins at plugins
     */
    public PluginAccessors getPlugins() {
        return paccForPluginAccessors;
    }

    public static class QuiltLibraryAccessors extends SubDependencyFactory {

        public QuiltLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for loader (org.quiltmc:quilt-loader)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getLoader() {
                return create("quilt.loader");
        }

            /**
             * Creates a dependency provider for mappings (org.quiltmc:quilt-mappings)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getMappings() {
                return create("quilt.mappings");
        }

    }

    public static class QuiltedLibraryAccessors extends SubDependencyFactory {
        private final QuiltedFabricLibraryAccessors laccForQuiltedFabricLibraryAccessors = new QuiltedFabricLibraryAccessors(owner);

        public QuiltedLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at quilted.fabric
         */
        public QuiltedFabricLibraryAccessors getFabric() {
            return laccForQuiltedFabricLibraryAccessors;
        }

    }

    public static class QuiltedFabricLibraryAccessors extends SubDependencyFactory {
        private final QuiltedFabricApiLibraryAccessors laccForQuiltedFabricApiLibraryAccessors = new QuiltedFabricApiLibraryAccessors(owner);

        public QuiltedFabricLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at quilted.fabric.api
         */
        public QuiltedFabricApiLibraryAccessors getApi() {
            return laccForQuiltedFabricApiLibraryAccessors;
        }

    }

    public static class QuiltedFabricApiLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public QuiltedFabricApiLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for api (org.quiltmc.quilted-fabric-api:quilted-fabric-api)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> asProvider() {
                return create("quilted.fabric.api");
        }

            /**
             * Creates a dependency provider for deprecated (org.quiltmc.quilted-fabric-api:quilted-fabric-api-deprecated)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getDeprecated() {
                return create("quilted.fabric.api.deprecated");
        }

    }

    public static class VersionAccessors extends VersionFactory  {

        private final QuiltVersionAccessors vaccForQuiltVersionAccessors = new QuiltVersionAccessors(providers, config);
        private final QuiltedVersionAccessors vaccForQuiltedVersionAccessors = new QuiltedVersionAccessors(providers, config);
        public VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: minecraft (1.20.1)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getMinecraft() { return getVersion("minecraft"); }

        /**
         * Returns the group of versions at versions.quilt
         */
        public QuiltVersionAccessors getQuilt() {
            return vaccForQuiltVersionAccessors;
        }

        /**
         * Returns the group of versions at versions.quilted
         */
        public QuiltedVersionAccessors getQuilted() {
            return vaccForQuiltedVersionAccessors;
        }

    }

    public static class QuiltVersionAccessors extends VersionFactory  {

        public QuiltVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: quilt.loader (0.19.1)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getLoader() { return getVersion("quilt.loader"); }

            /**
             * Returns the version associated to this alias: quilt.mappings (1.20.1+build.1)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getMappings() { return getVersion("quilt.mappings"); }

    }

    public static class QuiltedVersionAccessors extends VersionFactory  {

        private final QuiltedFabricVersionAccessors vaccForQuiltedFabricVersionAccessors = new QuiltedFabricVersionAccessors(providers, config);
        public QuiltedVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of versions at versions.quilted.fabric
         */
        public QuiltedFabricVersionAccessors getFabric() {
            return vaccForQuiltedFabricVersionAccessors;
        }

    }

    public static class QuiltedFabricVersionAccessors extends VersionFactory  {

        public QuiltedFabricVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: quilted.fabric.api (7.0.3+0.83.1-1.20.1)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getApi() { return getVersion("quilted.fabric.api"); }

    }

    public static class BundleAccessors extends BundleFactory {
        private final QuiltedBundleAccessors baccForQuiltedBundleAccessors = new QuiltedBundleAccessors(objects, providers, config, attributesFactory, capabilityNotationParser);

        public BundleAccessors(ObjectFactory objects, ProviderFactory providers, DefaultVersionCatalog config, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) { super(objects, providers, config, attributesFactory, capabilityNotationParser); }

        /**
         * Returns the group of bundles at bundles.quilted
         */
        public QuiltedBundleAccessors getQuilted() {
            return baccForQuiltedBundleAccessors;
        }

    }

    public static class QuiltedBundleAccessors extends BundleFactory {
        private final QuiltedFabricBundleAccessors baccForQuiltedFabricBundleAccessors = new QuiltedFabricBundleAccessors(objects, providers, config, attributesFactory, capabilityNotationParser);

        public QuiltedBundleAccessors(ObjectFactory objects, ProviderFactory providers, DefaultVersionCatalog config, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) { super(objects, providers, config, attributesFactory, capabilityNotationParser); }

        /**
         * Returns the group of bundles at bundles.quilted.fabric
         */
        public QuiltedFabricBundleAccessors getFabric() {
            return baccForQuiltedFabricBundleAccessors;
        }

    }

    public static class QuiltedFabricBundleAccessors extends BundleFactory {

        public QuiltedFabricBundleAccessors(ObjectFactory objects, ProviderFactory providers, DefaultVersionCatalog config, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) { super(objects, providers, config, attributesFactory, capabilityNotationParser); }

            /**
             * Creates a dependency bundle provider for quilted.fabric.api which is an aggregate for the following dependencies:
             * <ul>
             *    <li>org.quiltmc.quilted-fabric-api:quilted-fabric-api</li>
             *    <li>org.quiltmc.quilted-fabric-api:quilted-fabric-api-deprecated</li>
             * </ul>
             * This bundle was declared in catalog libs.versions.toml
             */
            public Provider<ExternalModuleDependencyBundle> getApi() {
                return createBundle("quilted.fabric.api");
            }

    }

    public static class PluginAccessors extends PluginFactory {
        private final QuiltPluginAccessors paccForQuiltPluginAccessors = new QuiltPluginAccessors(providers, config);

        public PluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of plugins at plugins.quilt
         */
        public QuiltPluginAccessors getQuilt() {
            return paccForQuiltPluginAccessors;
        }

    }

    public static class QuiltPluginAccessors extends PluginFactory {

        public QuiltPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Creates a plugin provider for quilt.loom to the plugin id 'org.quiltmc.loom'
             * This plugin was declared in catalog libs.versions.toml
             */
            public Provider<PluginDependency> getLoom() { return createPlugin("quilt.loom"); }

    }

}

package dart.henson.plugin.internal;

import com.android.build.gradle.api.BaseVariant;

import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.logging.Logger;

import java.util.HashMap;
import java.util.Map;

import static dart.henson.plugin.internal.ArtifactManager.NAVIGATION_ARTIFACT_PREFIX;
import static dart.henson.plugin.internal.ConfigurationManager.NAVIGATION_CONFIGURATION_SUFFIX_API;
import static dart.henson.plugin.util.StringUtil.capitalize;
import static java.lang.String.format;

public class DependencyManager {

    private Project project;
    private Logger logger;
    private ArtifactManager artifactManager;
    private ConfigurationManager configurationManager;

    public DependencyManager(Project project,
                             Logger logger,
                             ArtifactManager artifactManager,
                             ConfigurationManager configurationManager) {
        this.project = project;
        this.logger = logger;
        this.artifactManager = artifactManager;
        this.configurationManager = configurationManager;
    }

    public void addDartAndHensonDependenciesToNavigationConfigurations(String prefix, String dartVersionName) {
        DependencyHandler dependencies = project.getDependencies();
        String compileOnly = format("%sNavigationCompileOnly", prefix);
        String processors = format("%sNavigationAnnotationProcessor", prefix);
        String apiRuntime = format("%sNavigationApi", prefix);

        String android = "com.google.android:android:4.1.1.4";
        String dartRuntime = format("com.f2prateek.dart:dart:%s", dartVersionName);
        String hensonRuntime = format("com.f2prateek.dart:henson:%s", dartVersionName);
        String hensonProcessor = format("com.f2prateek.dart:henson-processor:%s", dartVersionName);
        String dartProcessor = format("com.f2prateek.dart:dart-processor:%s", dartVersionName);
        String dartAnnotations = format("com.f2prateek.dart:dart-annotations:%s", dartVersionName);

        dependencies.add(compileOnly, android);
        dependencies.add(compileOnly, dartRuntime);
        dependencies.add(compileOnly, hensonRuntime);
        dependencies.add(processors, hensonProcessor);
        dependencies.add(processors, dartProcessor);
        dependencies.add(apiRuntime, dartAnnotations);
    }

    public void addNavigationArtifactsToVariantConfiguration(BaseVariant variant) {
        System.out.println("addNavigationArtifactsToVariantConfiguration for variant: " + variant.getName());
        //we use the api configuration to make sure the resulting apk will contain the classes of the navigation jar.
        String configurationName = variant.getName() + "Api";
        String artifactName = artifactManager.getNavigationArtifactName(variant);
        Map<String, Object> map = new HashMap(2);
        map.put("path", project.getPath());
        map.put("configuration", artifactName);
        project.getDependencies().add(configurationName, project.getDependencies().project(map));
    }
}

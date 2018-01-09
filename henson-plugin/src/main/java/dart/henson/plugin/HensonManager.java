/*
 * Copyright 2013 Jake Wharton
 * Copyright 2014 Prateek Srivastava (@f2prateek)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dart.henson.plugin;

import com.android.build.gradle.api.BaseVariant;

import org.gradle.api.Project;
import org.gradle.api.logging.Logger;

import java.io.File;
import java.security.InvalidParameterException;

import dart.henson.plugin.internal.DependencyManager;
import dart.henson.plugin.internal.TaskManager;

public class HensonManager {
  private final Project project;
  private final Logger logger;
  private final TaskManager taskManager;
  private final DependencyManager dependencyManager;
  private final HensonPluginExtension hensonExtension;

  public HensonManager(Project project) {
    this.project = project;
    this.logger = project.getLogger();
    this.taskManager = new TaskManager(project, logger);
    this.dependencyManager = new DependencyManager(project, logger);
    this.hensonExtension = (HensonPluginExtension) project.getExtensions().getByName("henson");
  }

  public void createHensonNavigatorGenerationTask(BaseVariant variant) {
    if (hensonExtension == null || hensonExtension.getNavigatorPackageName() == null) {
      throw new InvalidParameterException(
          "The property 'henson.navigatorPackageName' must be defined in your build.gradle");
    }
    String hensonNavigatorPackageName = hensonExtension.getNavigatorPackageName();
    File destinationFolder = project.file(new File(project.getBuildDir(), "generated/source/navigator/" + variant.getName()));
    taskManager.createHensonNavigatorGenerationTask(variant, hensonNavigatorPackageName, destinationFolder);
    variant.addJavaSourceFoldersToModel(destinationFolder);
  }

  public void addDartAndHensonDependenciesToVariantConfigurations(String dartVersionName) {
    dependencyManager.addDartAndHensonDependenciesToVariantConfigurations(dartVersionName);
  }
}

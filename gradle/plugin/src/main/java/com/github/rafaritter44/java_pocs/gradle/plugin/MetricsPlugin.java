package com.github.rafaritter44.java_pocs.gradle.plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Set;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class MetricsPlugin implements Plugin<Project> {

	@Override
	public void apply(final Project project) {
		final MetricsPluginExtension extension = project
				.getExtensions()
				.create("metrics", MetricsPluginExtension.class);
		project.task("metrics").doLast(task -> {
			final String srcFolder = new StringBuilder()
					.append("src")
					.append(File.separator)
					.append("main")
					.append(File.separator)
					.append(extension.getLang())
					.toString();
			final Set<File> srcFiles = project.fileTree(srcFolder).getFiles();
			final int topLevelClasses = srcFiles.size();
			final long linesOfCode = srcFiles
					.parallelStream()
					.map(File::toPath)
					.flatMap(path -> {
						try {
							return Files.lines(path);
						} catch (final IOException e) {
							e.printStackTrace();
							throw new RuntimeException(e.getMessage(), e);
						}
					})
					.count();
			System.out.println("Number of Lines of Code: " + linesOfCode);
			System.out.println("Number of Top-Level Classes: " + topLevelClasses);
			System.out.println("Average Lines of Code per Top-Level Class: " + linesOfCode / topLevelClasses);
		});
	}

}

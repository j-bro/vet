/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java project to get you started.
 * For more details take a look at the Java Quickstart chapter in the Gradle
 * user guide available at https://docs.gradle.org/4.5.1/userguide/tutorial_java_projects.html
 */

plugins {
    // Apply the java plugin to add support for Java
    java

    // Apply the application plugin to add support for building an application
    application
}

application {

    // Define the main class for the application
    mainClassName = "App"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_9
    targetCompatibility = JavaVersion.VERSION_1_9
}

dependencies {
    // This dependency is found on compile classpath of this component and consumers.
    compile("com.google.guava:guava:23.0")
    compile("commons-cli:commons-cli:1.4")
    compile("org.eclipse.jgit:org.eclipse.jgit:4.10.0.201712302008-r")
    compile("org.slf4j:slf4j-api:1.7.25")
    compile("com.urswolfer.gerrit.client.rest:gerrit-rest-java-client:0.8.13")

    // Use JUnit test framework
    testCompile("junit:junit:4.12")
}

// In this section you declare where to find the dependencies of your project
repositories {
    // Use jcenter for resolving your dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
}

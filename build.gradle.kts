import org.jetbrains.changelog.*
import org.jetbrains.kotlin.gradle.tasks.*
import org.jetbrains.kotlin.utils.*

plugins {
	id("org.jetbrains.kotlin.jvm") version "1.9.25"
	id("org.jetbrains.intellij") version "1.17.4"
	id("org.jetbrains.grammarkit") version "2022.3.2.2"
	id("org.jetbrains.changelog") version "2.0.0"
}

group = providers.gradleProperty("pluginGroup").get()
version = providers.gradleProperty("pluginVersion").get()

intellij {
	pluginName.set(providers.gradleProperty("pluginName"))
	type.set(providers.gradleProperty("intellijType"))
	version.set(providers.gradleProperty("intellijVersion"))
}

grammarKit {
	jflexRelease.set("1.7.0-2")
}

repositories {
	mavenCentral()
	maven("https://www.jetbrains.com/intellij-repository/releases")
}

dependencies {
	//JUNIT
	testImplementation("junit:junit:4.13.2")
}

sourceSets {
	main {
		java.srcDirs("src/main/java", "src/main/kotlin", "src/main/gen")
		resources.srcDirs("src/main/resources")
	}
	test {
		java.srcDirs("src/test/java", "src/test/kotlin")
		resources.srcDirs("src/test/resources")
	}
}

kotlin {
	jvmToolchain(17)
}

tasks {
	withType<Copy> {
		duplicatesStrategy = DuplicatesStrategy.INCLUDE //需要加上
	}
	withType<KotlinCompile> {
		kotlinOptions {
			jvmTarget = "17"
			freeCompilerArgs = listOf(
				"-Xjvm-default=all",
				"-Xinline-classes",
				"-opt-in=kotlin.RequiresOptIn",
				"-opt-in=kotlin.ExperimentalStdlibApi",
			)
		}
	}
	test {
		useJUnitPlatform()
	}
	withType<Jar> {
		from("README.md", "README_en.md", "LICENSE")
	}
	patchPluginXml {
		fun String.toChangeLogText(): String {
			val regex1 = """[-*] \[ ].*""".toRegex()
			val regex2 = """[-*] \[X].*""".toRegex(RegexOption.IGNORE_CASE)
			return lines()
				.run {
					val start = indexOfFirst { it.startsWith("## ${version.get()}") }
					val end = indexOfFirst(start + 1) { it.startsWith("## ") }.let { if(it != -1) it else size }
					subList(start + 1, end)
				}
				.mapNotNull {
					when {
						it.contains("(HIDDEN)") -> null //hidden
						it.matches(regex1) -> null //undo
						it.matches(regex2) -> "*" + it.substring(5) //done
						else -> it
					}
				}
				.joinToString("\n")
				.let { markdownToHTML(it) }
		}

		sinceBuild.set(providers.gradleProperty("sinceBuild"))
		untilBuild.set(providers.gradleProperty("untilBuild"))
		pluginDescription.set(projectDir.resolve("DESCRIPTION.md").readText())
		changeNotes.set(projectDir.resolve("CHANGELOG.md").readText().toChangeLogText())
	}
    buildPlugin {
        //重命名插件包
        archiveBaseName.set(providers.gradleProperty("pluginPackageName"))
    }
	runIde {
		systemProperties["idea.is.internal"] = true
		jvmArgs("-Xmx4096m")
	}
	publishPlugin {
		token.set(providers.environmentVariable("IDEA_TOKEN"))
	}
}

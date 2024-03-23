import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.plugin.KaptExtension
import org.jetbrains.kotlin.gradle.tasks.KaptGenerateStubs

class KaptPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.kapt")
            }
            extensions.configure<KaptExtension> {
                correctErrorTypes = true
            }

            tasks.withType(KaptGenerateStubs::class.java).configureEach {
                kotlinOptions {
                    jvmTarget = "11"
                }
            }
        }
    }
}
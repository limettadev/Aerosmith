rootProject.name = "aerosmith"

includeBuild("build-logic")

this.setupSubproject("aerosmith-server", "Aerosmith-Server")
this.setupSubproject("aerosmith-api", "Aerosmith-API")
this.setupSubproject("paperclip", "paperclip")

fun setupSubproject(name: String, dir: String) {
    include(":$name")
    project(":$name").projectDir = file(dir)
}

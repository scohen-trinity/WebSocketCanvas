package models

object CodeGen extends App {
    slick.codegen.SourceCodeGenerator.run(
        "slick.jdbc.PostgresProfile",
        "org.postgresql.Driver",
        "jdbc:postgresql://localhost/messagelist?user=scohen&password=password",
        "C:/Users/sammy/Downloads/Web Apps/Task 9/play-tasks-scohen-trinity/server/app/",
        "models", None, None, true, false
    )
}

// object CodeGen extends App {
//     slick.codegen.SourceCodeGenerator.run(
//         "slick.jdbc.PostgresProfile",
//         "org.postgresql.Driver",
//         url = "jdbc:postgresql://localhost/messagelist",
//         outputDir = "C:/Users/sammy/Downloads/Web Apps/Task 9/play-tasks-scohen-trinity/server/app/",
//         pkg = "models",
//         user = Some("scohen"),
//         password = Some("password"),
//         ignoreInvalidDefaults = true,
//         outputToMultipleFiles = false
//     )
// }
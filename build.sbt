import AssemblyKeys._

name := "neoid-reindexer"

scalaVersion := "2.10.2"

libraryDependencies ++= Seq(
  "org.neo4j" % "neo4j-kernel" % "1.9.5" exclude("org.neo4j", "neo4j-udc"),
  "org.neo4j" % "neo4j-lucene-index" % "1.9.5"
)

fork := true

assemblySettings

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
  {
    case "META-INF/LICENSES.txt" => MergeStrategy.concat
    case "META-INF/CHANGES.txt"  => MergeStrategy.concat
    case x => old(x)
  }
}

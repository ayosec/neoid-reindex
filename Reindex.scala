
import org.neo4j.graphdb.Node
import org.neo4j.graphdb.Transaction
import org.neo4j.graphdb.PropertyContainer
import org.neo4j.graphdb.index.AutoIndexer
import org.neo4j.graphdb.factory.GraphDatabaseFactory
import org.neo4j.tooling.GlobalGraphOperations
import scala.collection.JavaConversions._

object Reindex extends App {

  final val propName = "neoid_unique_id"

  val graph = new GraphDatabaseFactory().newEmbeddedDatabase(args(0))

  def applyAutoIndex(index: AutoIndexer[_]): Unit = {
    index.startAutoIndexingProperty("neoid_unique_id")
    index.setEnabled(true)
  }

  applyAutoIndex(graph.index().getNodeAutoIndexer())
  applyAutoIndex(graph.index().getRelationshipAutoIndexer())

  val operations = GlobalGraphOperations.at(graph)
  var tx = graph.beginTx()
  var iterations = 0

  try {
    loop(operations.getAllNodes)
    loop(operations.getAllRelationships)
    tx.success()
  } finally {
    tx.finish()

    graph.shutdown()
  }

  def loop(records: Iterable[PropertyContainer]): Unit = {

    for(record <- records) {

      iterations = iterations + 1
      if(iterations > 1000) {
        print(".")
        iterations = 0
        tx.success()
        tx.finish()
        tx = graph.beginTx()
      }

      reindex(record)

    }

  }

  def reindex(record: PropertyContainer): Unit =
    if(record.hasProperty(propName))
      record.setProperty(propName, record.getProperty(propName))

}

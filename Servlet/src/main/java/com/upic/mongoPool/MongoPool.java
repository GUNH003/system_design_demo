package com.upic.mongoPool;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ReadPreference;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Indexes;
import org.bson.Document;

/**
 * MongoPool class. It represents a MongoDB connection pool. Singleton pattern is used.
 */
public class MongoPool {

  // To use a MongoDB cluster, deploy multiple instances, set up replica set, and change the connection accordingly.
  // e.g. "mongodb://<IP_0>:27017,<IP_1>:27017,<IP_2>:27017/?replicaSet=<replica_set_name>";
  private static final String MONGODB_CONNECTION_STRING = "mongodb://localhost:27017/";  // TODO: check before run
  private static final String MONGODB_DATABASE_NAME = "UPIC";                            // TODO: check before run
  private static final String MONGODB_COLLECTION_NAME = "LiftRide";                      // TODO: check before run
  private static final int MAX_CONNECTIONS = 128;
  private static final int MIN_IDLE_CONNECTIONS = 64;
  private final MongoClient mongoClient;
  private final MongoCollection<Document> collection;
  private static MongoPool instance = null;

  /**
   * Constructor for MongoPool class. Read operations prioritize replicas. Indexes are set to
   * facilitate queries.
   *
   * @param connectionString the connection url
   * @param maxConnections   the maximum connections
   * @param minIdle          the minimum idle connections
   */
  private MongoPool(String connectionString, int maxConnections, int minIdle) {
    MongoClientSettings settings = MongoClientSettings.builder()
        .applyConnectionString(new ConnectionString(connectionString))
        .readPreference(ReadPreference.secondaryPreferred())
        .applyToConnectionPoolSettings(builder -> {
          builder.maxSize(maxConnections);
          builder.minSize(minIdle);
        })
        .build();
    this.mongoClient = MongoClients.create(settings);
    this.collection = mongoClient.getDatabase(MONGODB_DATABASE_NAME)
        .getCollection(MONGODB_COLLECTION_NAME);
    this.collection.createIndex((Indexes.ascending("resortID", "seasonID", "dayID")));
    this.collection.createIndex((Indexes.ascending("skierID", "seasonID", "dayID")));
    this.collection.createIndex((Indexes.ascending("skierID", "resortID")));
    this.collection.createIndex((Indexes.ascending("skierID", "resortID", "seasonID")));
  }

  /**
   * Gets the MongoPool instance.
   *
   * @return the MongoPool instance
   */
  public static MongoPool getInstance() {
    if (instance == null) {
      instance = new MongoPool(MONGODB_CONNECTION_STRING, MAX_CONNECTIONS, MIN_IDLE_CONNECTIONS);
    }
    return instance;
  }

  /**
   * Gets the reference to the collection.
   *
   * @return the reference to the collection
   */
  public MongoCollection<Document> getCollection() {
    return this.collection;
  }

  /**
   * Closes the MongoDB connection pool.
   */
  public void close() {
    if (mongoClient != null) {
      mongoClient.close();
    }
  }
}

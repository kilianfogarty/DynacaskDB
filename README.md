# DynacaskDB

DynacaskDB is an experimental NoSQL key–value database written in Java.

At its core, DynacaskDB is based on the **Bitcask storage engine design** (append-only log files with an in-memory key index), with the long-term goal of supporting **distributed operations inspired by DynamoDB and Apache Cassandra**.

I started this project because I wanted to learn more about databases and distributed systems.

---

## Project Goals:

- Implement a **Bitcask-style storage engine**:
    - Append-only log-structured storage
    - In-memory hash index
    - Crash-safe recovery
    - Tombstones for deletes
    - Compaction (planned)

- Layer **distributed systems concepts** on top:
    - Node-based architecture
    - Partitioning and replication (planned)
    - Gossip or membership protocol (planned)
    - Eventually consistent reads/writes (planned)

- Visualization:
    - Dashboard backed by Springboot and React.js (planned)

#!/bin/bash

echo "Waiting for MongoDB to be ready..."
sleep 10

mongosh --host mongodb1:27017 --username admin --password adminpassword --authenticationDatabase admin <<EOF
rs.initiate(
  {
    _id: "replica_set_single",
    version: 1,
    members: [
      { _id: 0, host: "mongodb1:27017" },
      { _id: 1, host: "mongodb2:27017" },
      { _id: 2, host: "mongodb3:27017" }
    ]
  }
);
EOF

echo "Replica set initiated!"

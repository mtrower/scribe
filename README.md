# scribe
Realtime and logged instrumentation of a system accessible through a web
interface

### Problem Statement
Computer systems can at times seem opaque boxes.  Determining the state of
a misbehaving system can be an involved process spanning a great many tools.
This is especially true of headless server systems.  By the time a system can
be fully instrumented, the trouble may well have passed.  Worse yet is the
scenario where the trouble has passed before you even begin - in this case
conventioal instrumentation tools are of little to no value.

Scribe aims to aggregate essential system statistics and instrumentation in
one place, providing views from multiple depths, optionally across multiple
systems.  It will log all information gathered over time against potential
future review.

### Project Technologies/Techniques
* Modular design (daemonized gathering tools vs web front end)
* Security/Authentication
   * User accounts/roles: assign rights profiles to various users
* Database (PostgreSQL)
    * Store users and instrumentation data.
* Web Services or APIs
    * slack APIs
* Logging
    * General application state (milestones, error reporting, etc)
* Unit Testing
    * JUnit
* Independent Research Topic
    * Multi-threaded execution with Java
    * Non-blocking process execution with Java
    * Core system instrumentation via DTrace
    * Generation of functional/pleasing visuals (line graphs, pie charts,
      histograms, flame graphs, etc).

### [Development Journal](Journal.md)

# m4cd

A continuous delivery pipeline for M4.
Provides a simple repository that can be cloned
to build servers, syncing with one or more master
repositories and providing a web interface
for manual builds.

Currently supports automated cloning and
regression testing.  Will add deployment
capability to future versions.

## Usage

* `lein run` will start your pipeline with a web-ui listening on port 8080

For use in systems like Supervisor, the user should create an application
profile for m4cd, and use the associated runserver.sh as the daemon command.

Currently, port settings are defined in `/src/m4cd/core.clj` and default
to 8080 on localhost.  You probably want to redirect and proxy using
nginx or another service.

## Files

* `/`
    * `project.clj` contains dependencies and build configuration for Leiningen

* `/src/m4cd/`
    * `pipeline.clj` contains your pipeline-definition
    * `steps.clj` contains your custom build-steps
    * `core.clj` contains the `main` function that wires everything together

* `/resources/`
    * `logback.xml` contains a sample log configuration

## References

* for a more detailed example, look at the [example pipeline](https://github.com/flosell/lambdacd/tree/master/src/todopipeline) in the main LambdaCD project

# enjoy-reagent

This is the enjoy-reagent project. A project the uses the power of ClojureScript Interoperaliby and the [drag and drop](https://web.dev/drag-and-drop/) js api allowing us to create a drag and drop functional using only few lines of Clojure code.

## Requirements

[Install Clojure](https://clojure.org/guides/install_clojure)

[Install Leiningen](https://leiningen.org/)

## Development mode

To start the Figwheel compiler, navigate to the project folder and run the following command in the terminal:

```
lein figwheel
```

Figwheel will automatically push cljs changes to the browser. The server will be available at [http://localhost:3449](http://localhost:3449) once Figwheel starts up. 

Figwheel also starts `nREPL` using the value of the `:nrepl-port` in the `:figwheel`
config found in `project.clj`. By default the port is set to `7002`.

The figwheel server can have unexpected behaviors in some situations such as when using
websockets. In this case it's recommended to run a standalone instance of a web server as follows:

```
lein do clean, run
```

The application will now be available at [http://localhost:3000](http://localhost:3000).

## Demo

https://github.com/lksilva/enjoy-reagent/assets/8785700/47db2c7f-ccc6-4ed6-9eac-3d7eb2dfc594


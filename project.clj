(defproject vw "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :jvm-opts ["-Djava.library.path=/usr/local/lib"]

  :resource-paths ["lib/out.jar"
                   "resources"]
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [net.n01se/clojure-jna "1.0.0"]

                 [com.nativelibs4java/bridj "0.7.0"]])

(ns m4cd.pipeline
  (:use [lambdacd.steps.control-flow]
        [m4cd.steps])
  (:require
   [lambdacd.steps.shell :as shell]
   [lambdacd.steps.manualtrigger :as manualtrigger]
   [lambdacd-git.core :as git]))

#_(def pipeline-def
  `(
    manualtrigger/wait-for-manual-trigger
    some-step-that-does-nothing
    (in-parallel
      some-step-that-echos-foo
      some-step-that-echos-bar)
    manualtrigger/wait-for-manual-trigger
    some-failing-step))

;;from example...

;; buildsteps
#_(def some-repo "git@github.com:flosell/somerepo")

(def repo-uri  "https://github.com/fsdonks/m4.git")
(def repo-branch "master")

(defn wait-for-repo [_ ctx]
  (git/wait-for-git ctx repo-uri :ref (str "refs/heads/" repo-branch)))

#_(defn ^{:display-type :container} with-repo [& steps]
  (git/with-git repo-uri steps))

(defn clone [args ctx]
  (let [revision (:revision args)
        cwd      (:cwd args)
        ref      (or revision repo-branch)]
    (git/clone ctx repo-uri ref cwd)))

(defn run-some-tests [{cwd :cwd} ctx]
  (shell/bash ctx cwd
              "lein test"))

(defn compile-and-deploy [{cwd :cwd} ctx]
  (shell/bash ctx cwd
              "./buildscripts/compile-and-deploy.sh"))

;;more modern example...
(def pipeline-def
  `(
    (either
     manualtrigger/wait-for-manual-trigger
     wait-for-repo)
    (with-workspace
      clone
      run-some-tests)))

;; the pipeline
#_(def pipeline
  `(
    (either
     wait-for-manual-trigger
     wait-for-repo)
    (with-repo
      run-tests
      #_compile-and-deploy)))

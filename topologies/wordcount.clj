(ns wordcount
  (:use     [streamparse.specs])
  (:gen-class))

(defn tweetcount [options]
   [
    ;; spout configuration
    {"sentences-spout" (python-spout-spec
          options
          "spouts.sentences.SentencesSpout"
          ["sentence"]
          )
    }
    ;; bolt configuration
    {"parse-tweet-bolt" (python-bolt-spec
          options
          {"sentences-spout" :shuffle}
          "bolts.parsetweet.TweetParser"
          ["word"]
          :p 2
          )
     "wordcounter-bolt" (python-bolt-spec
          options
          {"parse-tweet-bolt" ["word"]}
          "bolts.wordcount.WordCounter"
          ["word" "count"]
          )
    }
   ]
)

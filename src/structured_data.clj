(ns structured-data)

(defn
  do-a-thing
  "First, doubles the input value. Then, returns the power of the result value by itself."
  [x]
  (let [xx (+ x x)]
    (Math/pow xx xx)))

(defn
  spiff
  "Returns the sum of the first and third index of the input vector."
  [v]
  (let [xx (get v 0)
        yy (get v 2)]
    (+ xx yy)))

(defn
  cutify
  "Adds a '<3' to the end of the input vector."
  [v]
  (let [xx "<3"]
    (conj v xx)))

(defn
  spiff-destructuring [v]
  "Returns the sum of the first and third index of the input vector."
  [v]
  (let [[x _ y] v]
    (+ x y)))

(defn
  point
  "Representation for a simple point with x and y values."
  [x y]
  [x y])

(defn
  rectangle
  "Representation of a rectangle, which takes two points as an input."
  [bottom-left top-right]
  [bottom-left top-right])

(defn
  width
  "Calculates the width of the rectangle."
  [rectangle]
  (let [[[^int x1 _] [^int x2 _]] rectangle]
    (Math/abs (- x1 x2))))

(defn
  height
  "Calculates the height of the rectangle."
  [rectangle]
  (let [[[_ ^int y1] [_ ^int y2]] rectangle]
    (Math/abs (- y1 y2))))

(defn
  square?
  "Returns true if the rectangle is a square."
  [rectangle]
  (let [xx (width rectangle)
        yy (height rectangle)]
    (if (== xx yy)
      true
      false)))

(defn
  area
  "Returns the area of input rectangle."
  [rectangle]
  (let [xx (width rectangle)
        yy (height rectangle)]
    (* xx yy)))

(defn
  contains-point?
  "Returns true if the rectangle contains the input point."
  [rectangle point]
  (let [[[^int x1 y1] [^int x2 y2]] rectangle
        [^int xx yy] point]
    (if (and (<= x1 xx x2) (<= y1 yy y2))
      true
      false)))

(defn
  contains-rectangle?
  "Returns true if the inner rectangle is inside the outer rectangle."
  [outer inner]
  (let [p1 (get inner 0)
        p2 (get inner 1)
        xx (contains-point? outer p1)
        yy (contains-point? outer p2)]
    (if (and xx yy)
      true
      false)))

(defn
  title-length
  "Returns the length of the books title."
  [book]
  (let [title (get book :title)]
    (count title)))

(defn
  author-count
  "Returns the amount of authors for the book."
  [book]
  (let [authors (get book :authors)]
    (count authors)))

(defn
  multiple-authors?
  "Returns true if the book has multiple authors."
  [book]
  (let [author-amount (author-count book)]
    (if (> author-amount 1)
      true
      false)))

(defn
  add-author
  "Adds a new author to the input book."
  [book new-author]
  (let [authors (get book :authors)]
    (assoc book :authors (conj authors new-author))))

(defn
  alive?
  "Returns true if the author doesn't have a death-year."
  [author]
  (let [xx (contains? author :death-year)]
    (if (false? xx)
      true
      false)))

(defn
  element-lengths
  "Returns the length of every element in the collection."
  [collection]
  (let [lengths (map count collection)]
    lengths))

(defn
  second-elements
  "Return a sequence of second elements inside a collection."
  [collection]
  (let [seconds (fn [c] (get c 1))]
    (map seconds collection)))

(defn
  titles
  "Returns the titles of the books."
  [books]
  (map :title books))

(defn
  monotonic?
  "Returns true if the sequence is monotonic."
  [a-seq]
  (or (apply <= a-seq) (apply >= a-seq)))

(defn
  stars
  "Returns a string with n-amount of asterisks."
  [n]
  (apply str (apply concat (repeat n "*"))))

(defn
  toggle
  "Adds the element to the set if it doesn't exists OR removes the element from the set if it does exist."
  [a-set elem]
  (if (contains? a-set elem)
    (disj a-set elem)
    (conj a-set elem)))

(defn
  contains-duplicates?
  "Returns true if the sequence contains duplicates, otherwise false."
  [a-seq]
  (let [seq-count (count a-seq)
        set-count (count (set a-seq))]
    (not= seq-count set-count)))

(defn
  old-book->new-book
  "Modifies the book authors map into a set."
  [book]
  (let [authors (get book :authors)]
    (assoc book :authors (set authors))))

(defn
  has-author?
  "Returns true if the given author is in the book."
  [book author]
  (let [authors (get book :authors)]
    (contains? authors author)))

(defn
  authors
  "Returns all authors as a set."
  [books]
  (apply clojure.set/union (map :authors books)))

(defn
  all-author-names
  "Returns a set of names of all the authors in the books."
  [books]
  (set (map :name (authors books))))

(defn
  author->string
  "Creates a string representation of a single author."
  [author]
  (let [name (:name author)
        byear (:birth-year author)
        dyear (:death-year author)]
    (str name (if byear
                (str " (" byear " - " dyear ")")))))

(defn
  authors->string
  "Creates a string representation of the given set of authors"
  [authors]
  (apply str (interpose ", " (map author->string authors))))

(defn
  book->string
  "Creates a string representation of a single book."
  [book]
  (str (:title book)
       (if (:authors book)
         (str ", written by " (authors->string (:authors book))))))

(defn
  books->string
  "Creates a string representation of the given set of books"
  [books]
  (let [book-count (count books)]
    (str
      (cond
        (== book-count 0) "No books."
        (== book-count 1) "1 book."
        :else (str book-count " books.")
        )
      (apply str (map (fn [book] (str " " (book->string book) ".")) books)))))

(defn
  books-by-author
  "Returns the books written by given author."
  [author books]
  (filter (fn [book] (has-author? book author)) books))

(defn
  author-by-name
  "Searches for authors from collection with given name. Returns nil if the author doesn't exist."
  [name authors]
  (first (filter (fn [author] (= name (:name author))) authors)))

(defn
  living-authors
  "Returns a sequence of living authors from the input authors."
  [authors]
  (filter (fn [author] (alive? author)) authors))

(defn
  has-a-living-author?
  "Returns true if there is a living author for the book."
  [book]
  (if (empty? (living-authors (:authors book)))
    false
    true))

(defn
  books-by-living-authors
  "Returns those books that have a living author."
  [books]
  (filter (fn [book] (= true (has-a-living-author? book))) books))

; %________%

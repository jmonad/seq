package com.jmonad.seq;

import com.jmonad.seq.lambda.Action;
import com.jmonad.seq.lambda.BinaryFunction;
import com.jmonad.seq.lambda.Function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Seq<T> implements Comparable<Seq<T>>, Iterable<T> {
  private List<T> list;

  @SafeVarargs
  public Seq(T... params) {
    this.list = new ArrayList();
    Collections.addAll(this.list, params);
  }

  public Seq(List<T> list) {
    this.list = list;
  }

  public void add(T item) {
    this.list.add(item);
  }

  /**
   * Applies a function to each item in the list and returns the original list.
   * Used for side effects.
   * @param fn
   * @return Seq<T>
   */
  public Seq<T> each(Action<T> fn) {
    for (T item : this.list) {
      fn.call(item);
    }
    return this;
  }

  /**
   * Applies a function to each item in the list, and produces a new list with
   * the results. The length of the result is the same length as the input.
   * @param fn
   * @return Seq<T>
   */
  public <Ret> Seq<Ret> map(Function<Ret, T> fn) {
    Seq<Ret> buffer = new Seq();
    for (T item : this.list) {
      buffer.add(fn.call(item));
    }
    return buffer;
  }

  /**
   * Returns a new list which contains only the truthy values of the inputted
   * list.
   * @return Seq<T>
   */
  public Seq<T> compact() {
    Seq<T> buffer = new Seq();
    for (T item : this.list) {
      if (item != null && !item.equals(false)) {
        buffer.add(item);
      }
    }
    return buffer;
  }

  /**
   * Returns a new list composed of the items which pass the supplied
   * function's test.
   * @param fn
   * @return Seq<T>
   */
  public Seq<T> filter(Function<Boolean, T> fn) {
    Seq<T> buffer = new Seq();
    for (T item : this.list) {
      if (fn.call(item)) {
        buffer.add(item);
      }
    }
    return buffer;
  }

  /**
   * Like filter, but the new list is composed of the items which fail the
   * function's test
   * @param fn
   * @return Seq<T>
   */
  public Seq<T> reject(Function<Boolean, T> fn) {
    Seq<T> buffer = new Seq();
    for (T item : this.list) {
      if (!fn.call(item)) {
        buffer.add(item);
      }
    }
    return buffer;
  }

  /**
   * Equivalent to `{ xs.filter(f), xs.reject(f) }`, but more efficient,
   * using only one loop.
   * @param fn
   * @return Seq<Seq<T>>
   */
  public Seq<Seq<T>> partition(Function<Boolean, T> fn) {
    Seq<Seq<T>> output = new Seq();
    Seq<T> success = new Seq();
    Seq<T> failure = new Seq();

    for (T item : this.list) {
      if (fn.call(item)) {
        success.add(item);
      } else {
        failure.add(item);
      }
    }

    output.add(success);
    output.add(failure);

    return output;
  }

  /**
   * Returns the first item in list to pass the function's test. Returns
   * null if all items fail the test.
   * @param fn
   * @return T
   */
  public T find(Function<Boolean, T> fn) {
    for (T item : this.list) {
      if (fn.call(item)) {
        return item;
      }
    }
    return null;
  }

  /**
   * The first item of the list. Returns null if the list is empty.
   * @return T
   */
  public T head() {
    return this.list.size() > 0 ? this.list.get(0) : null;
  }

  /**
   * Everything but the first item of the list.
   * @return Seq<T>
   */
  public Seq<T> tail() {
    return new Seq(this.list.subList(1, this.list.size()));
  }

  /**
   * The last item of the list. Returns null if the list is empty.
   * @return T
   */
  public T last() {
    int size = this.list.size();
    return size > 0 ? this.list.get(size - 1) : null;
  }

  /**
   * Everything but the last item of the list.
   * @return Seq<T>
   */
  public Seq<T> initial() {
    int size = this.list.size();
    return size > 0 ? new Seq<T>(this.list.subList(0, size)) : new Seq<T>();
  }

  /**
   * Whether the list is empty.
   * @return boolean
   */
  public boolean empty() {
    return this.list.size() == 0;
  }

  /**
   * Returns a new list which is the reverse of the inputted one.
   * @return Seq<T>
   */
  public Seq<T> reverse() {
    Seq<T> buffer = new Seq<T>();
    for (int i = this.list.size() - 1; i >= 0; i--) {
      buffer.add(this.list.get(i));
    }
    return buffer;
  }

  /**
   * Returns a new list which contains each value of the inputted list only once.
   * @return Seq<T>
   */
  public Seq<T> unique() {
    Seq<T> buffer = new Seq<T>();
    for (T item : this.list) {
      if (!buffer.contains(item)) {
        buffer.add(item);
      }
    }
    return buffer;
  }

  /**
   * Returns a new list which contains each item which has a unique value when
   * applied to the supplied function. If there are multiple different items
   * with the same value when the function is applied, the first item is taken.
   * @param fn
   * @return Seq<T>
   */
  public <Ret> Seq<T> uniqueBy(Function<Ret, T> fn) {
    Seq<T> buffer = new Seq<T>();
    Seq<Ret> seen = new Seq<Ret>();

    for (T item : this.list) {
      Ret value = fn.call(item);
      if (seen.contains(value)) {
        continue;
      }
      seen.add(value);
      buffer.add(item);
    }
    return buffer;
  }

  /**
   * Takes a list of items, and using the binary function supplied, folds them
   * into a single value. Requires an initial value (the second argument),
   * which will be the starting point, and result in case of an empty list.
   * @param fn
   * @param memo
   * @return T
   */
  public T fold(BinaryFunction<T, T, T> fn, T memo) {
    for (T item : this.list) {
      memo = fn.call(memo, item);
    }
    return memo;
  }

  /**
   * Alias to fold.
   * @param fn
   * @param memo
   * @return T
   */
  public T foldl(BinaryFunction<T, T, T> fn, T memo) {
    return this.fold(fn, memo);
  }

  /**
   * Like fold, except assumes a non-empty list, and thus doesn't require an
   * initial value.
   * @param fn
   * @return T
   */
  public T fold1(BinaryFunction<T, T, T> fn) {
    return this.tail().fold(fn, this.head());
  }

  /**
   * Alias to fold1
   * @param fn BinaryFunction<T, T, T>
   * @return T
   */
  public T foldl1(BinaryFunction<T, T, T> fn) {
    return this.fold1(fn);
  }

  /**
   * Like fold, except folding from the right instead of the left.
   * @param fn
   * @param memo
   * @return T
   */
  public T foldr(BinaryFunction<T, T, T> fn, T memo) {
    for (T item : this.reverse().toArrayList()) {
      memo = fn.call(item, memo);
    }
    return memo;
  }

  /**
   * Like foldr, except assumes a non-empty list, and thus doesn't require an initial value.
   * @param fn
   * @return
   */
  public T foldr1(BinaryFunction<T, T, T> fn) {
    return this.initial().foldr(fn, this.last());
  }

  @SafeVarargs
  public final Seq<T> difference(Seq<T>... sequences) {
    Seq<T> buffer = new Seq();
    outer:
    for (T item : this.list) {
      for (Seq<T> seq : sequences) {
        if (seq.contains(item)) {
          continue outer;
        }
      }
    }
    return buffer;
  }

  public boolean contains(T item) {
    return this.list.contains(item);
  }

  public ArrayList<T> toArrayList() {
    return (ArrayList<T>) this.list;
  }

  @Override
  public Iterator<T> iterator() {
    return this.list.iterator();
  }

  @Override
  public int compareTo(Seq<T> another) {
    int mySize = this.list.size();
    int yourSize = another.list.size();
    return mySize < yourSize ? -1 : mySize > yourSize ? 1 : 0;
  }
}

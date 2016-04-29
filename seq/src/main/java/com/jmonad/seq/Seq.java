package com.jmonad.seq;

import com.jmonad.seq.lambda.Action;
import com.jmonad.seq.lambda.BinaryFunction;
import com.jmonad.seq.lambda.Function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

public class Seq<T> implements Comparable<Seq<T>>, Iterable<T> {
  protected List<T> list;

  /**
   * DEPRECATED: This is a unsafe way to create a Seq. Will be removed in the future.
   * @param params The Params to initialize a List
   */
  @SafeVarargs
  @Deprecated
  public Seq(T... params) {
    this.list = new ArrayList<>(params.length);
    this.addAll(params);
  }

  /**
   * Creates a internal ArrayList and initialize it with the values of the argument list
   * @param list A list to initialize internal list.
   */
  public Seq(List<T> list) {
    this.list = new ArrayList<>(list.size());
    this.list.addAll(list);
  }

  /**
   * Add item to list
   * @param item The item to add
   * @return true if the item has been added.
   */
  public boolean add(T item) {
    return this.list.add(item);
  }

  /**
   * Adds all items in list to self.
   *
   * @param items A item list
   * @return true if all items were added.
   */
  public boolean addAll(List<T> items) {
    return this.list.addAll(items);
  }

  @SafeVarargs
  public final boolean addAll(T... params) {
    return Collections.addAll(this.list, params);
  }

  /**
   * Applies a function to each item in the list and returns the original list.
   * Used for side effects.
   * @param fn The function to apply
   * @return Seq<T> Self
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
   * @param fn The function to apply
   * @return Seq<T> A new Seq with the result of the mapping
   */
  public <Ret> Seq<Ret> map(Function<Ret, T> fn) {
    Seq<Ret> buffer = new Seq<>();
    for (T item : this.list) {
      buffer.add(fn.call(item));
    }
    return buffer;
  }

  public boolean some(Function<Boolean, T> fn) {
    Seq<Boolean> buffer = new Seq<>();
    for(T item : this.list) {
      if(fn.call(item)) return true;
    }
    return false;
  }

  /**
   * Returns a new list which contains only the truthy values of the inputted
   * list.
   * @return Seq<T> A new list
   */
  public Seq<T> compact() {
    Seq<T> buffer = new Seq<>();
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
   * @param fn The matching function
   * @return Seq<T> A new list with items that match fn
   */
  public Seq<T> filter(Function<Boolean, T> fn) {
    Seq<T> buffer = new Seq<>();
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
   * @param fn The matching function
   * @return Seq<T> A new list with items that does not match fn
   */
  public Seq<T> reject(Function<Boolean, T> fn) {
    Seq<T> buffer = new Seq<>();
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
   * @param fn The matching function
   * @return Seq<Seq<T>> A Seq of two Seq with the first one being the matched and second unmatched.
   */
  public Seq<Seq<T>> partition(Function<Boolean, T> fn) {
    Seq<Seq<T>> output = new Seq<>();
    Seq<T> success = new Seq<>();
    Seq<T> failure = new Seq<>();

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
   * @param fn The matching function
   * @return T The first matching item, or null.
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
   * @return T The first item or null if empty list.
   */
  public T head() {
    return this.list.size() > 0 ? this.list.get(0) : null;
  }

  /**
   * Everything but the first item of the list.
   * @return Seq<T> A new Seq that contains everything but the first item
   */
  public Seq<T> tail() {
    return new Seq<>(this.list.subList(1, this.list.size()));
  }

  /**
   * The last item of the list. Returns null if the list is empty.
   * @return T Last item or null if list is empty
   */
  public T last() {
    int size = this.list.size();
    return size > 0 ? this.list.get(size - 1) : null;
  }

  /**
   * Everything but the last item of the list.
   * @return Seq<T> A new Seq that contains everything but the last item
   */
  public Seq<T> initial() {
    int size = this.list.size();
    return size > 0 ? new Seq<>(this.list.subList(0, size)) : new Seq<T>();
  }

  /**
   * Whether the list is empty.
   * @return boolean true if the list is empty
   */
  public boolean isEmpty() {
    return this.list.size() == 0;
  }

  /**
   * DEPRECATED: Use isEmpty instead.
   * @return boolean true if the list is empty.
   */
  @Deprecated
  public boolean empty() {
    return this.isEmpty();
  }

  /**
   * Returns a new list which is the reverse of the inputted one.
   * @return Seq<T>
   */
  public Seq<T> reverse() {
    Seq<T> buffer = new Seq<>();
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
   * @param fn The matching function
   * @return Seq<T>
   */
  public <Ret> Seq<T> uniqueBy(Function<Ret, T> fn) {
    Seq<T> buffer = new Seq<>();
    Seq<Ret> seen = new Seq<>();

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
   * Returns the first n elements of the sequence
   * @param amount
   * @return Seq<T>
   */
  public Seq<T> take(int amount) {
    if (amount <= 0) return new Seq<>();
    if (amount > this.list.size()) return new Seq<>(this.list);
    return new Seq<>(this.list.subList(0, amount));
  }

  /**
   * Remove the first n elements of the sequence
   * @param amount
   * @return Seq<T>
   */
  public Seq<T> drop(int amount) {
    if (amount <= 0) return new Seq<>(this.list);
    if (amount > this.list.size()) return new Seq<>();
    return new Seq<>(this.list.subList(amount, this.list.size()));
  }

  /**
   * Takes a list of items, and using the binary function supplied, folds them
   * into a single value. Requires an initial value (the second argument),
   * which will be the starting point, and result in case of an empty list.
   * @param fn The matching binary function
   * @param memo The Initial Value
   * @return T The folded value
   */
  public T fold(BinaryFunction<T, T, T> fn, T memo) {
    for (T item : this.list) {
      memo = fn.call(memo, item);
    }
    return memo;
  }

  /**
   * Alias to fold.
   * @param fn The matching function
   * @param memo The Initial Value
   * @return T The folded value
   */
  public T foldl(BinaryFunction<T, T, T> fn, T memo) {
    return this.fold(fn, memo);
  }

  /**
   * Like fold, except assumes a non-empty list, and thus doesn't require an
   * initial value.
   * @param fn The matching function
   * @return T The folded value
   */
  public T fold1(BinaryFunction<T, T, T> fn) {
    return this.tail().fold(fn, this.head());
  }

  /**
   * Alias to fold1
   * @param fn BinaryFunction<T, T, T>
   * @return T The folded value
   */
  public T foldl1(BinaryFunction<T, T, T> fn) {
    return this.fold1(fn);
  }

  /**
   * Like fold, except folding from the right instead of the left.
   * @param fn The Binary Function to fold
   * @param memo The initial value
   * @return T Folded Data
   */
  public T foldr(BinaryFunction<T, T, T> fn, T memo) {
    for (T item : this.reverse().toArrayList()) {
      memo = fn.call(item, memo);
    }
    return memo;
  }

  /**
   * Like foldr, except assumes a non-empty list, and thus doesn't require an initial value.
   * @param fn The Binary Function to fold
   * @return T The folded value
   */
  public T foldr1(BinaryFunction<T, T, T> fn) {
    return this.initial().foldr(fn, this.last());
  }

  /**
   * Returns a new Seq with the elements from this Seq that are not in Seq b
   * @param b The difference set.
   * @return A new Seq that is self - b
   */
  public final Seq<T> difference(Seq<T> b) {
    Seq<T> buffer = new Seq<>(this.list);
    buffer.list.removeAll(b.list);
    return buffer;
  }

  /**
   * Checks if this Seq contains an item.
   * <BR/> Item should have equals and hash code functions implemented to this work.
   * @param item An item to check
   * @return boolean True if the list contains item
   */
  public boolean contains(T item) {
    return this.list.contains(item);
  }

  /**
   * Returns a new ArrayList with the items of this Seq
   * @return ArrayList<T> a new ArrayList with the items of this Seq
   */
  public ArrayList<T> toArrayList() {
    return new ArrayList<>(this.list);
  }

  /**
   * Returns a new LinkedList with the items of this Seq
   * @return ArrayList<T> a new ArrayList with the items of this Seq
   */
  public LinkedList<T> toLinkedList() {
    return new LinkedList<>(this.list);
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

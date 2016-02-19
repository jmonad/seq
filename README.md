# Seq

[![Build Status](https://travis-ci.org/jmonad/seq.svg?branch=master)](https://travis-ci.org/jmonad/seq)
[![JitPack](https://img.shields.io/github/release/jmonad/seq.svg)](https://jitpack.io/#jmonad/seq)
[![Size of the library](https://img.shields.io/badge/Methods and size-48 | 6 KB-e91e63.svg)](http://www.methodscount.com/?lib=com.github.jmonad%3Aseq%3A0.2.1.0)

Test
----

The configuration of system that execute the tests are:

* (1) = Intel Core i7 4790K @ 4.4GHz, 16Gb DDR3 2400Mhz CL9
* (2) = MacBook Air Intel Core i5-5250U @ 2,7GHz, 4Gb LPDDR3 1600MHz

###map

Running a simple test, we could analysis the performance. It was great! Multiplying
values(range between 0...1000000) * 2, it takes 15.805456 millisecond to execute the
 test on the system (1). Running on device (2), it takes 414.887893 milliseconds!
 Almost 28 times slower than the i7.

###filter

The filter takes 0.022 milliseconds to run on device (2) the filter a small array, values { 1, 10, 100, 1000 }

Sample
------

###map

With Java <= 7:

```java
public class Foo {

  public Integer[] run() {

    Integer[] integers = {1,10,100};

    Function func = new Function<Integer, Integer>() {
      @Override public Integer call(Integer x) {
        return x * 2;
      }
    };

    Seq<Integer> values = new Seq<Integer>(integers).map(func);

    return values;
  }

}
```

With Java >= 8 or Retrolambda:

```java
public class Foo {

  public Integer[] run() {

    Integer[] integers = {1,10,100};

    Seq<Integer> values = new Seq<Integer>(integers).map(x -> x * 2);

    return values;
  }

}
```

This code will return `{ 2, 20, 200 }`.

###filter

If you need to filter something on your list, you simple can do this:

With Java <= 7:

```java
public class Foo {

    public Integer[] run() {

      Integer[] integers = { 1, 10, 100, 1000 };

      Function func = new Function<Boolean, Integer>() {
        @Override public Boolean call(Integer value) {
          return value > 1 && value < 1000;
        }
      };

      Seq<Integer> values = new Seq<Integer>(integers).filter(func);

      return values;
    }
}
```

With Java >= 8 or Retrolambda:

```java
public class Foo {

    public Integer[] run() {

      Integer[] integers = { 1, 10, 100, 1000 };

      Seq<Integer> values = new Seq<Integer>(integers).filter(value > 1 && value < 1000);

      return values;
    }
}
```

This code will return `{ 10, 100 }`.

Import dependency
--------------------------------

Into your build.gradle:

```groovy

repositories {
  maven {
    url "https://jitpack.io"
  }
}

dependencies {
  compile 'com.github.jmonad:seq:0.2.1.0'
}
```

Contributors
------------

* [Haskell Camargo][10]
* [Pedro Paulo de Amorim][11]
* [Vitor Prado][12]

License
-------

```
The MIT License (MIT)

Copyright (c) 2016 jMonad

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

[10]: https://github.com/haskellcamargo
[11]: https://github.com/ppamorim
[12]: https://github.com/vitorprado
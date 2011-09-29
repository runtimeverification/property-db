package hello.world;

/** ABCD GQRS
 * <p> FOOO! </p>
 * ASDF
 * ASDF
 *
 Hello 
 <br />
 <br />
 {@description.open}
   HELLO! {@new.open} YO! {@new.close}
 {@description.close}

 <br />

 <CODE>
 a
 b
 c
 d
 e
 f
 {@new.open}g
 h
 i
 j
 k{@new.close} FOOBAR!!!!!!!!!!
 </CODE>
 <br />
 <br />
 <br />
 {@property.open}
  This is a really bad propertyization that doesn't actually link to any property!
 {@property.close}
 <br />
 <br />
 <br />
 {@property.open Property:FOO}
 1 property
 {@property.close}
 <br />
 <br />
 <br />
 foo bar car

 <br />
 <br />
 <br />
 <br />
 <br />
 <br />
 foo bar car
 <br />
 <br />
 <br />
 {@description.open}
 foo bar car
 {@description.close}

 {@property.name foo/bar java.io.Foo}
 {@property.name foo/bar java.util.util2.Bar}

*/

public class Test {
  /** 
   *  This is the main method
   *    this is the second undecided portion
   *  {@property.open Property:java.io.Foo Property:java.util.util2.Bar me you are foo}
   *    WE HAVE A BUNCH O' PROPERTIES HERE!
   *  {@property.close}
   *  <br />
   *  <br />
   *  <br />
   *  {@property.open me you}
   *    Hello2!
   *  {@property.close}

   */

  public void main(){
    for(int i = 0; i < 255; ++i){
      System.out.print('a');
    }
  }
}


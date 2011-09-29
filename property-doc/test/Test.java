package hello.world;

/** ABCD GQRS
 * <p> FOOO! </p>
 * ASDF
 * ASDF
 *
 Hello 
 <br />
 {@property.link Foo Property Link}
 <br />
 {@descriptive.open}
   {@property.link  Foo Descriptive Link}
 {@descriptive.close}

 <br />

 {@undecided.open}
 <CODE>
 a
 b
 c
 d
 e
 f
 {@new.open}
 g
 h
 i
 j
 k
 {@new.close}
 </CODE>
 {@undecided.close}
 <br />
 <br />
 <br />
 {@formal.open}
  This is a really bad formalization that doesn't actually link to any property!
 {@formal.close}
 <br />
 <br />
 <br />
 {@formal.open}
 1 shortcut {@property.shortcut HasNext Boom shaka laka}
 {@formal.close}
 <br />
 <br />
 <br />
 {@undecided.open}
 foo bar car
 {@undecided.close}

 <br />
 <br />
 <br />
 {@formal.open}
 3 shortcuts 
   {@property.shortcut HasNext Boom shaka laka}
   {@property.shortcut Foo Fooo3}
   {@property.shortcut HasNext F000000000000}
 {@formal.close}
 <br />
 <br />
 <br />
 foo bar car
 <br />
 <br />
 <br />
 {@descriptive.open}
 foo bar car
 {@descriptive.close}
 @property.mop {@property.name HasNext}

//this property is HasNext, and stuff
HasNext(Iterator i) {
   event hasnext after(Iterator i) : 
      call(* Iterator. hasNext()) && target(i) {} 
   event next before(Iterator i) : 
      call(* Iterator.next()) && target(i) {}
   fsm :
     start [
        next -> unsafe
        hasnext -> safe
     ]
     safe [
        next -> start
        hasnext -> safe 
     ]
     unsafe [
        next -> unsafe
        hasnext -> safe
     ]
	
   \@unsafe {
      System.out.println("next called without hasNext!");
   }
}

 @property.mop  
   {@property.name Foo}
   LOREM IPSUM LOREM IPSUM
   LOREM IPSUM LOREM IPSUM
*/

public class Test {
  /** 
   *  This is the main method
   *  {@property.link HasNext HasNextProperty}
   *  {@undecided.open}
   *    this is the second undecided portion
   *  {@undecided.close}
   *  {@formal.open}
   *    Hello!
   *    {@property.shortcut Foo ++++}
   *    {@property.shortcut HasNext ----}
   *  {@formal.close}
   *  {@undecided.open} and so on...
   *  {@undecided.close}
   *  <br />
   *  <br />
   *  <br />
   *  {@formal.open}
   *    Hello2!
   *    {@property.shortcut Foo !!!!}
   *    {@property.shortcut HasNext <><><>}
   *  {@formal.close}

   */

  public void main(){
    for(int i = 0; i < 255; ++i){
      System.out.print('a');
    }
  }
}


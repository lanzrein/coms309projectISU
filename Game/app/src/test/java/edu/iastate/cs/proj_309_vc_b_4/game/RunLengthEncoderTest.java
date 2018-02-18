package edu.iastate.cs.proj_309_vc_b_4.game;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static edu.iastate.cs.proj_309_vc_b_4.game.utils.RunLengthEncoder.encode;
import static edu.iastate.cs.proj_309_vc_b_4.game.utils.RunLengthEncoder.decode;
import static org.junit.Assert.assertEquals;


/**
 * Created by johan on 14.09.2017.
 */

public final class RunLengthEncoderTest {


    @Test
    public void emptyList() throws Exception{
        assertEquals(new ArrayList<Byte>() ,encode(new ArrayList<Byte>()));
    }
    @Test(expected = IllegalArgumentException.class)
    public void refusesNegative()throws  Exception{
        List<Byte> xs = new ArrayList<Byte>();
        for(int i = 0; i < 10; i++){
            xs.add((byte)i);
        }
        xs.add((byte)-2);
        encode(xs);
    }


    //try with random cases.
    @Test
    public void encodeToDecode(){
        Random r= new Random(2017);
        for(int i = 0; i < 10; i++){
            List<Byte> xs = new ArrayList<>();
            int size = r.nextInt(50);
                    for(int j = 0 ; j < size; j++){
                        xs.add((byte)r.nextInt(10));
                    }
            assertEquals(xs,decode(encode(xs)));
        }



    }
    @Test
    public void moreCases(){
        List<Byte> xs = new ArrayList<Byte>();
        xs.addAll(Collections.nCopies(23,(byte)2));
        xs.addAll(Collections.nCopies(12,(byte)5));
        xs.addAll(Collections.nCopies(28,(byte)9));
        xs.addAll(Collections.nCopies(2,(byte)4));
        xs.addAll(Collections.nCopies(4,(byte)6));
        xs.addAll(Collections.nCopies(9,(byte)1));

        assertEquals(xs,decode(encode(xs)));


    }

}

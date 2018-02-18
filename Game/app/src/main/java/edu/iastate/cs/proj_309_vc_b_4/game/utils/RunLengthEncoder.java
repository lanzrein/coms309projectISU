package edu.iastate.cs.proj_309_vc_b_4.game.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static edu.iastate.cs.proj_309_vc_b_4.game.ArgumentChecker.requirePositive;

/**
 * This class give two utility methods to encode and decode a list of bytes
 * based on the RLE algorithm
 * PseudoCode taken from https://en.wikipedia.org/wiki/Run-length_encoding
 * Created by johan on 14.09.2017.
 */

public final class RunLengthEncoder {
    //empty constructor because we dont want the class to be created.
    private RunLengthEncoder(){};

    /**
     * Encode a list of bytes using a sort of RLE encoding.
     * Note : this method requires all byte to be positive to work.
     * @param xs the list of bytes to be encoded.
     * @return a list of byte encoded using a custom version of RLE
     */
    public static List<Byte> encode(List<Byte> xs){
        int count = 1;
        List<Byte> encoded = new ArrayList<Byte>();
        //we want to have a non empty list.
        requirePositive(xs.size());
        if(xs.isEmpty()){
            return encoded;
        }
        //get the first byte.
        byte curr = xs.get(0);
        for(int i = 1; i < xs.size();i++){
            requirePositive(xs.get(i));
            if(xs.get(i).equals(curr)){
                //if equal increase count
                count++;
            }else if(count > 2){
                //else we add first the index of RLE using a trick :
                //we take 2 - count. this makes the decoding very easy since all index will be negative
                encoded.add((byte)(2-count));
                encoded.add(curr);
                count = 1;
                curr = xs.get(i);
            }else{
                //add the number of count.
                encoded.addAll(Collections.nCopies(count,curr));
                count = 1;
                curr = xs.get(i);
            }
            //take care of limit case when we have lots of repetition and might
            //have a byte underflow.
            if(count > Byte.MAX_VALUE+2){
                encoded.add((byte)(2-count));
                encoded.add(curr);
                count = 0;
            }
        }

        //when we get out we have to add the last count.
        if(count > 2){
            encoded.add((byte)(2-count));
            encoded.add(curr);
        }else{
            encoded.addAll(Collections.nCopies(count,curr));
        }

        //return an unmodifiable version because immutability is important.
        return Collections.unmodifiableList(encoded);
    }

    /**
     * decodes a list that was encoded using the method encode from the RLE class
     * @param xs list to be decoded
     * @return list decoded
     */
    public static List<Byte> decode(List<Byte> xs){
        List<Byte> decoded = new ArrayList<Byte>();
        boolean decoding = true;
        for(int i = 0; i < xs.size(); i++){
            //if we need to decode the byte at that index
            if(decoding){
                //take a new pair.
                byte b = xs.get(i);
                if(b < 0){
                    //we have an index
                    int count = Math.abs(b)+2;
                    decoded.addAll(Collections.nCopies(count,xs.get(i+1)));
                    //we dont need to decoded the next one
                    decoding = false;
                }else if(xs.size() < i-1 && b == xs.get(i+1)){
                    //add twice the same byte.
                    decoded.add(b);
                    decoded.add(b);
                    decoding = false;

                }else{
                    decoded.add(b);
                }
            }else{
                //we will decoded the next byte.
                decoding = true;
            }
        }

        return Collections.unmodifiableList(decoded);
    }
}

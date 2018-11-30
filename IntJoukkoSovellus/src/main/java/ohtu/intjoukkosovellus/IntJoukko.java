
package ohtu.intjoukkosovellus;

import java.util.Arrays;
import java.util.Optional;

public class IntJoukko {

    public final static int DEFAULT_INITIAL_CAPACITY = 5; // aloitustalukon koko
    public final static int DEFAULT_CAPACITY_INCREASE = 5;  // luotava uusi taulukko on näin paljon isompi kuin vanha
    private int capacityIncrease;      // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] elements;         // Joukon luvut säilytetään taulukon alkupäässä.
    private int nextFreeIndex;  // Indeksi seuraavaan tyhjään paikkaan taulukossa

    public IntJoukko() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public IntJoukko(int initialCapacity) {
        this(initialCapacity, DEFAULT_CAPACITY_INCREASE);
    }


    public IntJoukko(int initialCapacity, int capacityIncrease) {
        if (initialCapacity < 0) {
            throw new IndexOutOfBoundsException("Virheellinen kapasiteetti");
        }
        if (capacityIncrease < 0) {
            throw new IndexOutOfBoundsException("Virheellinen capacityIncrease");
        }
        elements = new int[initialCapacity];
        nextFreeIndex = 0;
        this.capacityIncrease = capacityIncrease;
    }

    public boolean lisaa(int luku) {
        if (!kuuluu(luku)) {
            elements[nextFreeIndex] = luku;
            nextFreeIndex++;
            rebuild();
            return true;
        }
        return false;
    }

    private void rebuild() {
        if (nextFreeIndex % elements.length == 0) {
            elements = Arrays.copyOf(elements, elements.length + capacityIncrease);
        }
    }

    public boolean kuuluu(int element) {
        return findIndex(element).isPresent();
    }

    private Optional<Integer> findIndex(int value) {
        for (int i = 0; i < nextFreeIndex; i++) {
            if (value == elements[i]) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    public boolean poista(int element) {
        var index = findIndex(element);
        index.ifPresent(i -> {
            // Move last element of the backing array into the removed index
            elements[i] = elements[nextFreeIndex - 1];
            nextFreeIndex--;
        });
        return index.isPresent();
    }

    public int mahtavuus() {
        return nextFreeIndex;
    }


    @Override
    public String toString() {
        if (nextFreeIndex == 0) {
            return "{}";
        } else if (nextFreeIndex == 1) {
            return "{" + elements[0] + "}";
        } else {
            String tuotos = "{";
            for (int i = 0; i < nextFreeIndex - 1; i++) {
                tuotos += elements[i];
                tuotos += ", ";
            }
            tuotos += elements[nextFreeIndex - 1];
            tuotos += "}";
            return tuotos;
        }
    }

    public int[] toIntArray() {
        int[] taulu = new int[nextFreeIndex];
        for (int i = 0; i < taulu.length; i++) {
            taulu[i] = elements[i];
        }
        return taulu;
    }


    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko x = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            x.lisaa(aTaulu[i]);
        }
        for (int i = 0; i < bTaulu.length; i++) {
            x.lisaa(bTaulu[i]);
        }
        return x;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko y = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            for (int j = 0; j < bTaulu.length; j++) {
                if (aTaulu[i] == bTaulu[j]) {
                    y.lisaa(bTaulu[j]);
                }
            }
        }
        return y;

    }

    public static IntJoukko erotus(IntJoukko a, IntJoukko b) {
        IntJoukko z = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            z.lisaa(aTaulu[i]);
        }
        for (int i = 0; i < bTaulu.length; i++) {
            z.poista(i);
        }

        return z;
    }

}

package ohtu.intjoukkosovellus;

public class IntJoukko {

    public final static int DEFAULT_INITIAL_CAPACITY = 5; // aloitustalukon koko
    public final static int DEFAULT_CAPACITY_INCREASE = 5;  // luotava uusi taulukko on näin paljon isompi kuin vanha
    private int capacityIncrease;      // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] values;         // Joukon luvut säilytetään taulukon alkupäässä.
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
        values = new int[initialCapacity];
        nextFreeIndex = 0;
        this.capacityIncrease = capacityIncrease;
    }

    public boolean lisaa(int luku) {
        if (nextFreeIndex == 0) {
            values[0] = luku;
            nextFreeIndex++;
            return true;
        } else {
        }
        if (!kuuluu(luku)) {
            values[nextFreeIndex] = luku;
            nextFreeIndex++;
            if (nextFreeIndex % values.length == 0) {
                int[] taulukkoOld = values;
                kopioiTaulukko(values, taulukkoOld);
                values = new int[nextFreeIndex + capacityIncrease];
                kopioiTaulukko(taulukkoOld, values);
            }
            return true;
        }
        return false;
    }

    public boolean kuuluu(int luku) {

        int on = 0;
        for (int i = 0; i < nextFreeIndex; i++) {
            if (luku == values[i]) {
                on++;
            }
        }
        if (on > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean poista(int luku) {
        int kohta = -1;
        int apu;
        for (int i = 0; i < nextFreeIndex; i++) {
            if (luku == values[i]) {
                kohta = i; //siis luku löytyy tuosta kohdasta :D
                values[kohta] = 0;
                break;
            }
        }
        if (kohta != -1) {
            for (int j = kohta; j < nextFreeIndex - 1; j++) {
                apu = values[j];
                values[j] = values[j + 1];
                values[j + 1] = apu;
            }
            nextFreeIndex--;
            return true;
        }


        return false;
    }

    private void kopioiTaulukko(int[] vanha, int[] uusi) {
        for (int i = 0; i < vanha.length; i++) {
            uusi[i] = vanha[i];
        }

    }

    public int mahtavuus() {
        return nextFreeIndex;
    }


    @Override
    public String toString() {
        if (nextFreeIndex == 0) {
            return "{}";
        } else if (nextFreeIndex == 1) {
            return "{" + values[0] + "}";
        } else {
            String tuotos = "{";
            for (int i = 0; i < nextFreeIndex - 1; i++) {
                tuotos += values[i];
                tuotos += ", ";
            }
            tuotos += values[nextFreeIndex - 1];
            tuotos += "}";
            return tuotos;
        }
    }

    public int[] toIntArray() {
        int[] taulu = new int[nextFreeIndex];
        for (int i = 0; i < taulu.length; i++) {
            taulu[i] = values[i];
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
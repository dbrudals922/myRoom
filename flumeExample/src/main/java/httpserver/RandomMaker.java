package httpserver;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RandomMaker {
    private Integer seed;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RandomMaker that = (RandomMaker) o;
        return seed.equals(that.seed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seed);
    }

    public Integer getSeed() {
        return seed;
    }

    public void setSeed(Integer seed) {
        this.seed = seed;
    }

    public RandomMaker(Integer seed) {
        this.seed = seed;
    }
    public RandomMaker() {this.seed = 0;}

    //억지 스트림과 람다를 써 봄
    public List<Integer> getRandomThree(){
        System.out.println("random maker on!");
        List<Integer> result = Stream.iterate(new Random().nextInt(this.seed), x ->{
            return new Random().nextInt(this.seed);
        }).limit(3).collect(Collectors.toList());
        return  result;
    }
}

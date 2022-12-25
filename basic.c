#include <stdio.h>

int main()
{

//===========반복문==============
    // 1번
    int i = 1;
    int c=0;
    while(i<=10){
        c+=i;
        i++;
    }

    // 2번
    int i = 1;
    int c = 0;
    int n;
    scanf("%d", &n);

    while(i<=n){
        c+=i;
        i++;
    }



    // 3번
    int c = 0;
    int i = 0;
    while(i<5){
        int n;
        scanf("%d", &n);
        c+=n;
        i++;
    }



    // 4번
    int gcd;
    int a, b;
    scnaf("%d %d", &a, &b);



    // 5번
    int n, c=0;
    do{
        scanf("%d", &n);
        c+=n;
    }while(n!=0);



   // 6번
   int n;
   do{
    scanf("%d", &n);
   }while(n%2==0);



   // 7번
   int a, b;
   do{
    printf("입력하세요: ");
    scanf("%d %d", &a, &b);
   }while(a!=b);

    // 8번
    int i;
    for(i=1; i<=10; i++){
        printf("%d\n", i);
    }

    // 9번
    int i, c=0;
    for(i=1; i<=10; i++){
        c+=i;
    }



    // 10번
    int i, n;
    scanf("%d", &n);
    for(i=1; i<=n; i++){
        if(n%i==0){
            printf("%d\n", i);
        }
    }

    // 11번
    int i, j;
    for(i=1; i<=9; i++){
        printf("==================\n");
        for(j=1; j<=9; j++){
            printf("%d * %d = %d\n", i, j, i*j);
        }
    }

   // 12번
    int i, j, n;
    scanf("%d", &n);

    for (i=1; i <= n; i++){
        for(j=1; j<=n; j++){
            printf("%d * %d = %d\n",i, j, i*j);
        }
    }

    // 13번
    int n, i, j;
    printf("단을 입력하세요: ");
    scanf("%d", &n);

    for(i=1; i<=n; i++){
        for(j=1; j<i+1; j++){
            printf("%d ", j);
        }
        printf("\n");
    }

    return 0;
}

//===========함수============
    
// 1번
double square(double a){
    return a*a;
}

int main(){
    int n;
    printf("정수를 입력하시오: ");
    scanf("%d", &n);
    printf("주어진 정수 %d의 제곱은 %f입니다.", n, square(n));

    return 0;
}

// 2번

double get_radius(){
    int n;
    printf("원의 반지름을 입력하세요: ");
    scnaf("%d", &n);

    return n;
}

double cal_area(double radius){
    return radius*radius*3.141592;
}
int main(){

    int r = get_radius();

    printf("원의 면적은 %f입니다.", cal_area(r));

    return 0;
}


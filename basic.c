#include <stdio.h>

int main()
{

//===========�ݺ���==============
    // 1��
    int i = 1;
    int c=0;
    while(i<=10){
        c+=i;
        i++;
    }

    // 2��
    int i = 1;
    int c = 0;
    int n;
    scanf("%d", &n);

    while(i<=n){
        c+=i;
        i++;
    }



    // 3��
    int c = 0;
    int i = 0;
    while(i<5){
        int n;
        scanf("%d", &n);
        c+=n;
        i++;
    }



    // 4��
    int gcd;
    int a, b;
    scnaf("%d %d", &a, &b);



    // 5��
    int n, c=0;
    do{
        scanf("%d", &n);
        c+=n;
    }while(n!=0);



   // 6��
   int n;
   do{
    scanf("%d", &n);
   }while(n%2==0);



   // 7��
   int a, b;
   do{
    printf("�Է��ϼ���: ");
    scanf("%d %d", &a, &b);
   }while(a!=b);

    // 8��
    int i;
    for(i=1; i<=10; i++){
        printf("%d\n", i);
    }

    // 9��
    int i, c=0;
    for(i=1; i<=10; i++){
        c+=i;
    }



    // 10��
    int i, n;
    scanf("%d", &n);
    for(i=1; i<=n; i++){
        if(n%i==0){
            printf("%d\n", i);
        }
    }

    // 11��
    int i, j;
    for(i=1; i<=9; i++){
        printf("==================\n");
        for(j=1; j<=9; j++){
            printf("%d * %d = %d\n", i, j, i*j);
        }
    }

   // 12��
    int i, j, n;
    scanf("%d", &n);

    for (i=1; i <= n; i++){
        for(j=1; j<=n; j++){
            printf("%d * %d = %d\n",i, j, i*j);
        }
    }

    // 13��
    int n, i, j;
    printf("���� �Է��ϼ���: ");
    scanf("%d", &n);

    for(i=1; i<=n; i++){
        for(j=1; j<i+1; j++){
            printf("%d ", j);
        }
        printf("\n");
    }

    return 0;
}

//===========�Լ�============
    
// 1��
double square(double a){
    return a*a;
}

int main(){
    int n;
    printf("������ �Է��Ͻÿ�: ");
    scanf("%d", &n);
    printf("�־��� ���� %d�� ������ %f�Դϴ�.", n, square(n));

    return 0;
}

// 2��

double get_radius(){
    int n;
    printf("���� �������� �Է��ϼ���: ");
    scnaf("%d", &n);

    return n;
}

double cal_area(double radius){
    return radius*radius*3.141592;
}
int main(){

    int r = get_radius();

    printf("���� ������ %f�Դϴ�.", cal_area(r));

    return 0;
}


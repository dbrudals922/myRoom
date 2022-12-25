#include <stdio.h>

int main(){

    int n, i, j;
    printf("단을 입력하세요: ");
    scanf("%d", &n);
    
    for(i=1; i<10; i++){
        if(i!=n)
            continue;
        for(j=1; j<10; j++){
            printf("%d * %d = %d\n", i, j, i*j);
        }
    }

    return 0;
}
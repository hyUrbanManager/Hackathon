//
// Created by 黄业 on 2019-09-06.
//

#include <iostream>

using namespace std;

class A {

public:

    int mem;

    void doSomething() {
        cout << "do something" << endl;
    }

};

int main() {
    A a;
    cout << &a << endl;
    a.doSomething();

    A *pa;
    pa->doSomething();
    cout << pa->mem << endl;

    return 0;
}

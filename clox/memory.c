#include <stdlib.h>

#include "memory.h"
#include "vm.h"
#include "object.h"
void* reallocate(void* pointer, size_t oldSize, size_t newSize) {

    if (newSize == 0) {
        free(pointer);
        return NULL;
    }

    void* result = realloc(pointer, newSize);
    if(result == NULL) exit(1);
    return result;
}
void freeObjects() {
  Obj* object = vm.objects;
  while (object != NULL) {
    Obj* next = object->next;
    freeObjects(object);
    object = next;
  }
}
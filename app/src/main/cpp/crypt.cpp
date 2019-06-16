#include <jni.h>
#include <string>
#include <stdio.h>
#include "aes.h"
#include <android/log.h>

#define TAG "JNICrypt"
#define LOGI(...)  __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_wlh_jniencrypt_JniEncryptActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_wlh_jniencrypt_JniEncryptActivity_encryptFromJni(JNIEnv *env, jobject instance,
                                                                  jstring str_, jstring mKey_) {
    const char *str = env->GetStringUTFChars(str_, 0);
    const char *mKey = env->GetStringUTFChars(mKey_, 0);

    LOGI("wanlihua debug str=%s, mkey =%s\n",str,mKey);
    char encrypt[1024] = {0};
    AES aes_en((unsigned char*)mKey);
    aes_en.Cipher((char*)str,encrypt);
    LOGI("wanlihua debugencrypt = %s",encrypt);

    env->ReleaseStringUTFChars(str_, str);
    env->ReleaseStringUTFChars(mKey_, mKey);

    return env->NewStringUTF(encrypt);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_wlh_jniencrypt_JniEncryptActivity_decryptFromJni(JNIEnv *env, jobject instance,
                                                                  jstring str_, jstring mKey_) {
    const char *str = env->GetStringUTFChars(str_, 0);
    const char *mKey = env->GetStringUTFChars(mKey_, 0);

    LOGI("wanlihua debug str_des=%s, mkey =%s\n",str,mKey);
    char decrypt[1024] = {0};
    AES aes_de((unsigned char*)mKey);
    aes_de.InvCipher((char*)str,decrypt);
    LOGI("wanlihua debug decrypt = %s",decrypt);

    env->ReleaseStringUTFChars(str_, str);
    env->ReleaseStringUTFChars(mKey_, mKey);

    return env->NewStringUTF(decrypt);
}
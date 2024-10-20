package ru.ardyc.kairos.service

import kotlinx.coroutines.runBlocking
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.stereotype.Service
import ru.ardyc.tenshi.user.UserInfoServiceGrpcKt
import ru.ardyc.tenshi.user.getUserInfoByJwtRequest

@Service
class TenshiService {
    lateinit var tenshiClient: UserInfoServiceGrpcKt.UserInfoServiceCoroutineStub

    @GrpcClient("tenshi")
    fun setTenshiClient(tenshiClient: UserInfoServiceGrpcKt.UserInfoServiceCoroutineStub) {
        this.tenshiClient = tenshiClient
    }

    fun getUserByToken(token: String) =
        runBlocking {
            tenshiClient
                .getUserInfoByJwt(getUserInfoByJwtRequest { jwt = token })
        }
}

package ru.ardyc.kairos.service

import kotlinx.coroutines.runBlocking
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.stereotype.Service
import ru.ardyc.tenshi.user.UserInfoServiceGrpcKt
import ru.ardyc.tenshi.user.getUserInfoByJwtRequest
import ru.ardyc.tenshi.user.getUserInfoRequest
import java.util.UUID

@Service
class TenshiService {

    @GrpcClient("tenshi")
    private lateinit var tenshiClient: UserInfoServiceGrpcKt.UserInfoServiceCoroutineStub

    fun getUserByToken(token: String) = runBlocking {
        tenshiClient.getUserInfoByJwt(getUserInfoByJwtRequest { jwt = token })
    }

    fun getUserById(uuid: UUID) = runBlocking {
        tenshiClient.getUserInfoById(getUserInfoRequest { userId = uuid.toString() })
    }
}

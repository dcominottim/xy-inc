import org.springframework.cloud.contract.spec.ContractDsl.Companion.contract

contract {
    name = "get-pointsOfInterest"
    request {
        url = url("/pointsOfInterest")
        method = GET
    }
}
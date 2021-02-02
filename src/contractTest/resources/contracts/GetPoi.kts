import org.springframework.cloud.contract.spec.ContractDsl.Companion.contract

contract {
    request {
        url = url("/pointsOfInterest")
        method = GET
    }
    response {
        status = OK
    }
}
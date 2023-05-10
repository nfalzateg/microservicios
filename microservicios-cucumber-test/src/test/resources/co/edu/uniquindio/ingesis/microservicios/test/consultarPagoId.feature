Feature: The Pay API provide to user the function for register pay in the application
  Scenario Outline:: Yo como usuario registrado quiero consultar un pago realizado por id
    Given Soy un cliente con un token valido
    When usando el id de pago <id>
    And invoco el servicio de consultar pago
    Then obtengo un codigo de estado <code>
    And informacion del pago "<valido>"
    Examples:
    | id | code | valido |
    | 1  | 200  | true	 |
    | 2  | 404  | true	 |
    | 3  | 404  | true	 |
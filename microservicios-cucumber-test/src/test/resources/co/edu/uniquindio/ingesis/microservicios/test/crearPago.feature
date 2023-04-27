Feature: The Pay API provide to user the function for register pay in the application
  Scenario Outline:: Yo como usuario registrado quiero pagar una compra
    Given Soy un usuario con un token valido
    When usando el metodo de pago <medio-pago>
    And usando la referencia compra "<ref-compra>"
    And invoco el servicio de crear pago
    Then obtengo un status code <code>
    And voucher de verificacion
    Examples:
    | medio-pago | ref-compra    | code |
    | 1          | JKasaihsLDSO7 | 201  |
    | 2          | JKasaihsLDSO8 | 201  |
    | 3          | JKasaihsLDSO9 | 201  |
    | 4          | JKasaihsLDSO9 | 422  |
    
    
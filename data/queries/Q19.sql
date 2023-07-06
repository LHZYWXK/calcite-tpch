select
    sum(l_extendedprice * (1.0 - l_discount) ) as revenue
from
    lineitem,
    part where (
        p_partkey = l_partkey
        and p_brand = 'Brand#12'
        and p_container in ( 'SM CASE', 'SM BOX', 'SM PACK', 'SM PKG') and l_quantity >= 1 and l_quantity <= 1 + 10 and p_size between 1 and 5
        and l_shipmode in ('AIR', 'AIR REG')
        and l_shipinstruct = 'DELIVER IN PERSON'
    )
or (
    p_partkey = l_partkey
    and p_brand = 'Brand#23'
    and p_container in ('ED BAG', 'ED BOX', 'ED PKG', 'ED PACK') and l_quantity >= 10 and l_quantity <= 10 + 10 and p_size between 1 and 10
    and l_shipmode in ('IR', 'IR REG')
    and l_shipinstruct = 'ELIVER IN PERSON'
        )
or (
    p_partkey = l_partkey
    and p_brand = 'Brand#34'
    and p_container in ( 'G CASE', 'G BOX', 'G PACK', 'G PKG') and l_quantity >= 20 and l_quantity <= 20 + 10 and p_size between 1 and 15
    and l_shipmode in ('IR', 'IR REG')
    and l_shipinstruct = 'ELIVER IN PERSON'
        )
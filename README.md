# Calcite
Calcite backend project based on Spring Boot 2.7.12 and Calcite 1.34.0.

## Usage
### API
`localhost:8080/plan?sql=...`

## Notes
目前暂未使用 `TpchsSchema` 和 `TpchTableStatistic`，相当于直接把表挂在 `rootSchema` 下面。

类比：
1. TpchSchema -> Database
2. TpchTable -> Table

`TpchTableStatistic` 里面计划放表行数，通过 Scale Factor 来控制。

还不清楚是否会对 Validation 有影响，暂未实现。

## References
https://www.youtube.com/watch?v=meI0W12f_nw

https://github.com/zabetak/calcite-tutorial

https://liebing.org.cn/collections/calcite/

https://frankma.me/posts/papers/apache-calcite/
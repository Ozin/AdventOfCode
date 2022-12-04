package AOC2022;


import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import io.vavr.collection.Stream;

public class Day03 {
    public static void main(final String[] args) {
        System.out.printf("Solution for the first riddle: %s%n", one());
        System.out.printf("Solution for the second riddle: %s%n", two());
    }


    private static Object one() {
        return Stream.of(input())
                     .map(Rucksack::new)
                     .map(r -> r.left.intersect(r.right))
                     .map(Set::head)
                     .map(Day03::calculatePriority)
                     .sum();
    }

    private static Object two() {
        return Stream.of(input())
                     .map(Rucksack::new)
                     .map(Rucksack::whole)
                     .sliding(3, 3)
                     .map(group -> group.reduce(Set::intersect).head())
                     .map(Day03::calculatePriority)
                     .sum();
    }

    private static int calculatePriority(final char itemType) {
        return itemType > 96 ? itemType - 96 : itemType - 64 + 26;
    }

    record Rucksack(Set<Character> left, Set<Character> right) {
        public Rucksack(final String whole) {
            this(whole.substring(0, whole.length() / 2), whole.substring(whole.length() / 2));
        }

        public Rucksack(final String left, final String right) {
            this(left.toCharArray(), right.toCharArray());
        }

        public Rucksack(final char[] left, final char[] right) {
            this(HashSet.ofAll(left), HashSet.ofAll(right));
        }

        public Set<Character> whole() {
            return right.union(left);
        }
    }

    private static String[] testInput() {
        return """
                vJrwpWtwJgWrhcsFMMfFFhFp
                jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
                PmmdzqPrVvPwwTWBwg
                wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
                ttgJtRGJQctTZtZT
                CrZsJsPPZsGzwwsLwLmpwMDw
                """.stripIndent().split("\n");
    }

    private static String[] input() {
        return """
                VdzVHmNpdVmBBCpmQLTNfTtMhMJnhFhTTf
                FgqsZbqDDFqRrhhJnsnLMTfhJG
                bRRRPrRRwSwbDqgjvDZbRbQzpzmQVWCzzBdvQBFCzlWV
                GcDdRdvhRssRhGDdShCRtqWjlQzqWgqzNfNjfQWWjt
                mwwnnPFwmVrPmJmzfNzqCjQCbgVlgC
                nPnHHLrHwmJTrCTJpThBscBSdSLGZvZBvRhZ
                RVQQcVlcSRclfZCCCnMJJTSTnC
                NdHwjdwjbBBZrrZrbJDZJJ
                wmhjGGBGwwmjtjtdPlfRcpVQlhRppVJF
                pplbNBPPrppllrFNbpvppSTcwqcWFhTTShhJDTchqd
                RGzRfLjjmZmfmwLftTWhStStJWTdWmDm
                nfsMjQssnpPvNnrPrw
                SjjBgllzlQjBZvlBBgcFbgJHsMhJqbMHPggJbM
                hRLRVDdRRWnJqnnHTqMCnH
                GRfLddRRpVhNVrWSjwQQzSzcGSBQSc
                qMwNqqBdQdnTVBBVVhMVnVFzTHPggTPjGRDzvPTjjmvPDj
                sbSrWJpStrtPtRPttzmmDD
                pfbJJcbsrcLpWLllsnFmcqcwQncnQQqVNQ
                RBTWCMwCwdZThPZcZZ
                pVmVpHLFFFHHVgVmvNmHSQNvddlPPzZJMPcdhclhjczLdZMP
                vnnNnFStGMRDwWnn
                fWDdJTpDJzdBBBdmDSbSRHRwPqbPbHgSbz
                slQtQvNsMVvrrgPRgRglnhwWPH
                sGMMQFrsjvNMfWmdpfFDFZBf
                vnMRMWCMJwWWwwWPjmSdVmLdzvVbLrhL
                HsNfDHQlZpNqfQzbLbrqhjLmVdjd
                dfDZQsNpstHHHptZDDtZWgngtgBMPMMRwCPtBBGW
                HwQwwbwFNWHwHBVFQFLQzRznZnSzcjjjpPbcPpSP
                vTfTJsCmsftJZmTSSdPvzdjRSvPdjd
                TrGtTJfmGDfDhrhJJJsqrZhDBFLHHLLQWFwwlWBVBBVwgLFD
                FFTJRLccQgmTbSsbGm
                PBPPqCvCwqwhQQVhQngmVmSgglmGnHbnmb
                zqthvtQPBfCCzPwQPtwQzPwNLfNRFNLdLRLFRFFNLFdFdW
                nszjQnsPwjznzCCrhJqvjqhmBv
                tFWdHGWFGtctlNNpZBBhmqTrrbWqvTBT
                dlFtcpHDVVVHFdNGHGpGfQgsPDzSMsQwwPwgLLBQ
                TzQqTJGvnnSzqrWTnvfbbcflQcVltfcCMPVM
                jFjNZFFJLpFwmBwblcpptcVtfbbVlR
                jmmJdBBLNdGDWDDrdzqn
                pzddqQmGgbqgGpbJmmdnLZDCRZnZvFlLRZLSlLRT
                rVwchcBBMwVBHhHTZCTSGSCRTZlTDr
                HtccPfjfBhMtVBGHWpNqJdJdpjNJppWz
                WThTWWhtPbZRvvWbbvRTSRMjVRLLgFssgLpVsfSF
                JdwrlJcCwfzdqwwjsjzpLMgVsMFgML
                lQrwHNGJHClvTmfhBmPQmt
                lbRLhcLRpLJzgdGddF
                qvhwqDDCVtBDVhfMVGFnzGGzTBnGzGGgFg
                VjCwCWCMtjVDtChvQhtffcSmHpNWrrcHZHHZpplWbp
                DJVDVdvpmZdPgrCbgbgCJC
                lzczcWwwznGhBgPSvTlCrNgqNC
                wGzzQhzGGsBBGRBcQwGwnwjmmRHpRfmmMpppMjjHDvLL
                HJjJQWjFmmWtFmJTMchghhDwNMhVMWML
                SznPSRfRSSPdrrPSShbDVhbLPwcwGGwVNh
                ddRfzdRrCrRsZDSnFjspvFvqFqFqTvJt
                lflfjQfjvljfbfMLTTDCmHNLNVbL
                HSJnRrrJZJssnGRrnsrcqqRnDCLBMhVCTLVLhVNVJBBBhhBm
                SsrGGqqnSsWSnnqWHSrPfzftvFdvWlwfQgQwWvzz
                nQlsGnFGwwqNJWmJJjpplt
                HMTLPTRdvsTCCThDCZdLdLDNNpJBWJbjJMpBmbtNptBWmm
                DLCzPzTzZDdLdGSGfSGrsnQGzr
                LNPPLHNPHQNQSBFDWDPgggFv
                hszfWCWJhrBMsSSBgvFD
                GGZjfmJTjmZfrJrZrZJRGwNQnlLNHWjLVjlwdVNHpV
                BdNVdTcGVclmTwrTnwPwrHCr
                zttBWzfLsCggHPwDrf
                szsWSMbWzzbqBbzJjtjsvMzzvdmdVpGllpcRNZZhmRpZcGGc
                CjdbMmmmZFnzzgHlttGBVqtBGtsldG
                LvPPWNcFSSRslWhBsllT
                ppccvLPpcSNwLLwrDNNpLvwJHCMDmbCJbFzgmZZmFgbgnM
                TTNRwZqhcTTjsNTTsmrJlvrmmmqqHSrlJH
                fLQCCdtcfCDDVbVVQdFbQbdJHMHrJHrHnMllHdMHPrMdln
                WfQLQWWDbwRTWcRssN
                HQGQWHPDHNjMNQGNWNTWCvZllzqFZqzvvzhCtvFj
                DfgwdgfcFpchztvt
                sRggdwwVdgmnSTnnDBPBNWLn
                WbCZCfTVTTJjSwGdWNDGGw
                MMRqggMsqhlmlhrssHgRnRmRvdzdczvdNGNLzScGDrNzrLNc
                lRqsnRhmqqQnQpgQMlgDqRfBTJVFbJZQtBCbZQJVZFFb
                JnhQcCnmLDsmgmgr
                bbMZppRFGGRPfBMMRGMZssTTrLlLfsLlVLdsLsdn
                GZGSpPGMZtGGPFFRGBCwhvwjjcnJctvQcvHq
                vvrPrHZMGJNRMnqn
                BVChWWcDVWsBwCWwGrJNhRLJJnJtLqnq
                cjDfcfpWWsfWccBsHgPgrPTdpZbbgggv
                GshtVtVtjSCVtVvVGtlVvFZLMvLRZmHmZwbLwZdLdZmR
                JWzNDQzjcgJgQBJgzgMwLLHZZcdPwRLwRdHZ
                QWTppBWfDrrNBTTfffhFCpVSjnhCGsFtsqSl
                nmbCnzHHNzCjCJHJNSCWHLBLrvBrrSGRBDhrDRLrGL
                TVtPllwcgdmTRhLQTQhT
                fdFtccFcpPmggfdfNzHzCMsbCnWnJs
                fMgddvjgRRvjvjVJVdTlZGGtGnrlnqTccNjl
                HHSFSWSmmpbBpZlGncrNGbNtrn
                WDWBDDBDBDCwPBWBDWNQDgzvVvLRvsVLRwvwdJVLwL
                ZSmmvcpsmcJmJvqgBZgZqqtCtZjl
                WhDwhFSDgtBFjnFg
                rTrSTLWTTHNMNwNrMVddwNNhsmJGQcRsRcJGsJzQJsrzPsPm
                GBtLmPsCQqsGqgghZHDzzgLbFz
                zjjVTzTlRjRJfznrvrfpnNhFSghbbNFgHrbHZbDHbH
                vpfcTJVpcVlfcQPMPCGzCBsd
                HMhZNffcPZfNMrzjjFdGcJDjvJ
                VSBVVLlSQQmTVSWpSQzDrHzTTvDvFjFdGGzT
                mQSplVHWbHLSgWQnShNwsZMZPfbsNCRNCt
                MMqvDzLwZzlMqQfdGWPfgPffPglH
                ShTcJshsrRdnrdfrrfHp
                VVRtFhsCJVJVvwQqDdbDQd
                dmnNMlFNvmvljnbpMWNDFQvfQJJGvfPCfHGgQQgcJg
                bRVzLBSSTRBRBBrwTrVtRwCfcGHcsJgJgzgGsggHCzcC
                ZbtVTTrrqrSSVwhqqwBRwFWMDFNdjdZpWjdDDppjMW
                MTzqtbLtwFzJgbHgfbdWWH
                VMNBjNVjvNfhhhhfNPhP
                jmGMvlDZZnVMtzlwzqqCpwFt
                PpzGspGmpPsFLrTnTLzzBg
                QCWvfjfWjRPFZgrvqrBvTg
                wwNRCNQQVNRWjNWfQbHCCClHGDGJdGhpdhtPGhltDlJD
                dhbpGzhllzGlPvnzNcvtNVnc
                gcFMsTJDMMwrZqfjjqvvfnPtqJ
                sWRWTRFwrTgLDDFWgMsTlpSlpbSCdWWdcbmpChGd
                QccdFFFcFbcQPQPHMgpPMp
                NJlNSSMLDfJfmlSqHZRNpRqNBRPRPq
                LlMmJfvDVVTJSmVMscsCFtvwcjWjrjCj
                NVVMGWFSMRVGWSthwhTJWzcJCcJsTs
                jqRLqlfRZcmjcCzT
                rlRRrdrflpdvPbHpflfPlfDBgBMQpGVQMgpVDGMggBDV
                VwRhccRsnQStRhtGQVQVsmjgDgqJdggDjqLDgJlLzmLl
                BWFZpWHBNCBCNBzBNvWBpzHZqqlMqgNdlllDdqDgJDLlfDdd
                pFbTrrrBzbzTtSwStQnnsrVn
                DRfFbFqzbddfPFtsJnJRsnClJRsn
                cgjgQgWvSLVQgmWWgWVjVSSSBTltThLnqJssnTCZsTThntZT
                qjwpSrmWgcSrGMfdFDFdwHFd
                RWjDDWDjDNjjgDtSRRgjcjzFpnzwdFbFNdbFbpnldwFF
                vQfPfTQJbZdThTzL
                PrBQJQsfQqrrbfmPqMBfJbggjRVgWjttsHRSgRctDjSs
                NgqNWqqWWdnJdqpBNFtCmJGCDHttDGDsHsHm
                BjvzhRLTrTBQhTMQRjRRcjPGtmDCZZDZSCmmMSSZmVmSSt
                vQzRvRzQcPcvfQzRnddppFgnFfWwBFlb
                nnPvfvgrtPDHgvvGTRRRPZQGpGCLLV
                FlBsBdbllFdfWpbGMCVMZLVbZQ
                lhchNcqFsJBlBszztvwHjvzgrzmzffgH
                zZhdjTpJJpjmmpPZhvqnnZHqZcggvgMbgv
                tFpFQFSFtBGlFNwFfNMnHfbHcnvcvcfvcqrM
                GBFlNLSNVGVSSGtQSLLBBlNtphDdzpmmPmTPhRmdzdVCCDdR
                rpRCCDLpmnCdJCjn
                vMhSFvgsMGLmnmWMmm
                wVqFFvwvPPHhFhhgHPwHshpqrDDzqlfRbpftRLblrllr
                CRNDzdJCVDWzVgDjdjzRJzWRMTbHsMNZNbZMMbsfhTtMTLMB
                wSlwQcSpqPpcqcqFSqpwslsTfZtLhtlthtBHtTMZ
                SPGFGFFmpcPGDrWDmjDJVffR
                dsmdtJthJphWqHRPnRRsvvnnfR
                cDBMDDDlBZglDZTMDfzVvNRrvNPVHzRRTV
                PGMCCDClBDDbbFqmmhqQdpWGmmWp
                BJjcGhcvCnBdGHsmHSzZDzSDMHmRMQ
                qLWPLVrTwWlwwwrfrFfGDNmDQRMbQMzzmmbQLNMM
                rVWrFlGqlqwVwVGgWGphnvgBBsnvsjdBnCBnBg
                sNNsfBsmcGmgNTcHHSpnTWHnpV
                QlrhlrlMglhDQrdFblvFtMdnDWwSHDWWwnTSjLwVDSwwwT
                QtdMvltZhbFlPPZbQtQthZQdqCsJJGzBqqCBmCNCqgRCBsfP
                SZnQnnHRWRQRVjHnqlJTQPfdlqfJftqG
                pDzmbDBFbBLvvzttfdlTTl
                gsDLLpcmsSZVwlnRsV
                LHsWjwjWqCLsqCHcLsjdLqcdbpMGZPPtBhthbZBpBhMllwPG
                VFnVbbvJSfbgphSpGlhRBBSP
                JrrTgmFgzvNbrmNnmnvzgTLjCQWDLDCsTjssjqcHLc
                QmwwqTqsrdqNNqgtvnVDVcGNNtvv
                WBFBpzzjSJBJzJbfntgPzVzcvPnzDf
                HcpbHZJBFpjpcSZrZsdRQZrCwrwd
                JqmLmbtTWThBTWvWGVSrrVDsSGSG
                wwzRzNjNNbsPVPds
                jfgQRZwpQclQfffHgpRpwpfTcqtLLqCbbFFFLmbmTTBnFB
                fGpcccNNqcctqGMprvMPmbbzFSflSRzPBBlBbS
                JCjnjTZTTGPSGmTFPb
                ZWHhJjHLDVDgHLLDGjnhctsstwqctNwWqNwwQrtv
                sDwQhcwhBDDwrhGsQnRBQHHMHHMNJMZFCFRbCRftMM
                zjjlmjqfdTqlWdzTqmLzlzVjNCHJNHNFMFtbJNZgVNMMCCtN
                vPTfLmPTLWBsPDnSscnS
                ngznwDPPTzhPPDCTQnTTDQBQqHNNrHFVppbbjRFFqFhHqRqr
                tZJtcGsGtLLcctRqVBbbqrspbHNq
                BmZSvGBMdWPzMPgnnz
                MpNWPVNWWZWVVNZHVcvJjgBjJMStMJSjjg
                rzdCzrCTTLRCslvJDSjjdScgDm
                RLhCQzqTCssThRQzRzwGQrrCFffbfWppNpWNWVcHqZbHpVPp
                zQzCVWdSSjCdjpchWcGftflGZcgG
                RwnJTJwmvFHTBFmtBccZZfBGMstllM
                wwvvHRwqDnHFrmqnrSbQVVQfSbqQjbqjbQ
                ttDftStSlftPgSHmJbFwnMnFwzbrLHMMzz
                GqTBqhBqBvppBvMMTznrCbCnLwfr
                RBjjpZZvvZqGcNhjjpNmDPfDcsgfDfgScsQQQg
                rsSFccvBHppHPsvQrSHSprFjnbLGdbzLfbGLLtLjjzLzvl
                JWWJhmwwTDTGtnzlhdbtLG
                CWNqWRNCwnCJVppQFFFFNrgHBB
                MSRVnMjnVRVnPlcsrtMtschgDl
                NWHBwJBwBBQCHHqwWQGBNgdrFFtsthcqdltdDsqttq
                CCTTGCNCCBfNJNNWbGGnvVzDSRfDRSZvLPSzRn
                MpRfjRjWpZzzzRzZSpjzZjTCQcGdHLWNGqdBdcBWWBLccn
                lrbrsPQDPQglDtwggcLCqnCdNNdHBLsqNd
                blwbJggvgbwlvQbvtgwmvVwRfTzfMMjFVfSFjZjMTSTSzj
                ttSGjHWVrwWrWWvhzvhmhDfR
                qMBdNNsccQgfDRzRmqlhRl
                gQJdfJPdQBsMggMjPrTCLjrGrCrtVT
                tGFdlwDwGFdNtStghWWdQFSnTVfCfZhrfVTVCVprnRRhVn
                cLsBPQJsQPmbmPHTnRRnHprCVfns
                MjmvPqqQjPbQzjLwwDWDSlzSlGSgwl
                NSCpFgfbscbZZZwrtgPZJT
                zGCQlVGmmQGVqqJwGtHZGrPHHRTH
                qQvVmvzmqCdhhjzCQLjljLQMnMDSFWcSfnMfpbfnNcFFbFDN
                zFgqjQBmWNlWlfHrHdLc
                wnbCpSSZZTJSJSnmdrtHfGtftlcpltpH
                ZSwhVPPJgNVmNFzs
                WNVJthVHRRfLqpqN
                gdCGcCgJBCrgScRLzbjQQLfRRR
                SCFdGSFvlhTJsnvW
                FFZwFZZwRmFFhHtNLNLGRtsqjLMt
                gbDnnrMbMCffMPbPLNjGNlcppNtspp
                rgbzrzDrgVgnrBzFWMWmWBwHWHShSB
                zjRVjDqzRjvSBnBGGsfsrFsV
                fLccLLZpJMctwJWWWJWpJGCwFwsgnngFBPsCnnTBPT
                LbJlZNWMtpMlHRNHzdfSDfdj
                VGbbnJGSTsVTssTTnVVWMtfBBmvftRHfHBMJJfZp
                ghqtrzgPrjdzQCjmZMHfRHZHBmQmmB
                gltFtDqFVlTVWlTl
                HqNqZDTvNvVTLPSTvzfrfHfdndffwnbdnwrH
                MpSJlFcMJmcpFlmClcMcRnWbWtthrnfwnCGrrWfrwC
                RjcJJmSFMRQpMRFjMNVvTZjNPPvLTBPBBB
                MzClDtlzJzFzNGGm
                bjcLRHlTBsFJGmRm
                HcPSSfTSpLZLbSwtrtvMnlDCDPCl
                gWWgQJCsVhgRLCWsdjpmcBHvfvrrnvCvBB
                TqDtztqtStlbNTPtllqZpvmcFHjNjvjNvHvmrrmj
                ztPPGZqTPSbJgchGgwRQgQ
                wVrdtTqtCCvbNgbNTTDN
                mhGzWhGzMGWGrRmbFLBHZRNHNvZvgB
                hhGhShpnsSrqVCVSSj
                HnlbmGnlHZHnlBcjgwfDVfwLsGLGLDgR
                WhWMWTvQPWPLDMFRCDMsVD
                QdzJQPSPZqJnJRnZ
                TTjTjFBcRBGjwsDTBLmrCftfRVrrCftCVNRP
                WnqbJWnnQJhSqVfVPfDnggfrVN
                hSlDMllvhbQqllZlSWQdSQTBjsFHBjTwGdHBTBszLzcc
                rNWqWDLZWcqFqLLLgQQJnndnQdNzzJVMzd
                cPtsPvChtRsGswHPGbwcPcdVnpzvnmBmVvJBJdJVJdzn
                PfRfRGtsHsSRftbbbbHhwCCsjZgSTgSZWDTcgDZjLqgqFWLq
                BNzPnPJNNMwHJRhBGRWRdjFQddFlFjWd
                npbZrgnLSCSrWFjjdlZGlcDF
                gqtmmngSbmgHJqfqzNBHBJ
                stgzttBPRRRdpSVVpdpS
                WJFcLQmJZHcCFLJmcZLMfbpGSWMNpGftSSpMrb
                mcvvQvHmtLCJmHZQHZHCDHJJnjqPjjPzwvwhnwPqTjPBzPnB
                DDmbbPqgFSbSQPtPQJttrltJ
                CRfcnZWmRRhJNVtsVnQlsp
                fcBvWvWzcZWCzTTCTTvccFMBHGDdSwGFFbqwFSGSmF
                tCRBPCPRjzsJszBmtjmCvSpHcppJpvZdHHHcHZJG
                qbrlLnWlQDQDNvmHHHHcrZZvdm
                nmnWnnWmgQsCFzFCRVCg
                sHMHCDZfcwMcRcLMcZDCRCHMPdJqgjvVdvqgdgfdJbQgvWQb
                FFhTzmzGrnmtjTBjBBprrmFtqGgQqvVdPbbgqQQWJvvWJQqV
                TrNjrnFSlwNZNlNL
                JQGdsdzSzsdFQFSdssnndNlZjNPTJZNljVjTPhVPhT
                GgGLfRmHGLhNVjjNTLhV
                HvGvwpbHHRwpBrvBgSSzSFndtzndCrsFMd
                DptFshMrhDhDwmPPhwSNhmmS
                RLdcdRvBjnvRVcvlcLbCcbCwwpGBwSmfZqqPPPPwzmfqZq
                VlRLvVjCJLnlpvvRdllLcJJWDHgMMHDDHtWFFDDQgH
                SsSdrndpDlCdLftd
                VGPVJgmQrVGHHZfwLlfCTmfwlDlT
                PcJHcGgcWWbJpjRrphRbFpRn
                PdPSMHMLzPPSShBdffMMzMRHQQrpppcqTCQQpCccTGTRCnCQ
                vsbWmFbmJmZFFsmsbfpGVrGnWrrpVpnnVcTV
                JZslstfZNNSSPdlSMwlM
                bLLzRzZLbRqJJrDGGVZdwssDvGQw
                FCtNJlTFtmPfldSvDvQFVVsjSv
                PmCPHBhhPpWLWgzgHJ
                qwmwFHCgPgPPqPwMCrHHFBVVRBttVRRffVfmsjVNNB
                WSvcSnvbSWbhcbjlQbvlSQhlfBGcspVVsVGfVscpzpNcpBtN
                hJSLhlvlTjPFHMLCCq
                bggDpTggncGVVWbQcG
                sRvSwwwFBSpFzvRvMFZqmPmMVqmcmPPVqhqqWq
                SRBBrFZZwrddBFRjlptnLgDnTggdtd
                PPfMcZMflbMQcMllPVfTVMwjWWmZvpWWpWhhjjpdWWww
                sDQNnzsnQgDNsFzFqtGjGmWWSpWrGhdpvphdSW
                nNQNqLBHLqzDnHgnVJfPJPCRBbfCcJlT
                fppppWsjcSDPjjDpGhgwbfTgHTCbHJwbHbTR
                rLBdQnvMNMmFPbLqHqTqgLHCgL
                nrFznvMNMQdttrBcScsDstWcPGtWSc
                lclnRSDnGZtvSwnZDZzhLffqdsCNwTBCBBdNsd
                mPjmjmrFFpmQjMJQjlNdlhCsCLsTlNLs
                ggmrHHVVQVPJpWrgpWScRvzZzGGRnZvlgzZn
                GjGJGQJGcMTVfFDQzNVQzP
                mHqdbmmdnJqVzVhRVNzPbR
                wStmHJsJsLZLjTvM
                QssMbVGdMQjZPjwVwHVZPZClllvgSgvlTgwwSSCgSCtC
                WmmFBmJrcFRBFrJJBFchzWCStgCTgvhvTlfsNqfTlvTv
                sFzzssDLzrBPjDVVddHMQD
                fztDZSGrNrlnbnPTgFFpln
                jvvQMMcLcjJmQwHdJvjQJnnbTbRFRphnnpsWgmFRPR
                vTLHHCQLHBBjJCSZrVCZtSfSfrft
                gHfHffHLjwHrRjLrLRZVMnTdTBsNTBwTVBsBnN
                DWPhqhhDhvSGvWPzSzMBQBQVMMBBmvssvQvQ
                CDGbqCDbChSbWGrHcHRgbcVcfrLJ
                frlTLmtllbbbdpJS
                qFjhzjThjHTFGHTjqhhjMzBhVpVpdbBnSJQRpBnVVdbRRQJd
                vjWPWjWPPPWgwmfCrNvTvZ
                """.stripIndent().split("\n");
    }
}

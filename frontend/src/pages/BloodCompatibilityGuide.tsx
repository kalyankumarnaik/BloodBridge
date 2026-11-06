import React, { useState, useEffect } from 'react';
import { compatibilityApi } from '../services/api';
import { BloodGroup } from '../types';
import { MedicalDisclaimer } from '../components/Disclaimers';
import { BloodGroupBadge } from '../components/Badges';
import { ShieldCheck, Info, CheckCircle2, XCircle, Sparkles, BookOpen } from 'lucide-react';

export const BloodCompatibilityGuide: React.FC = () => {
  const [selectedGroup, setSelectedGroup] = useState<BloodGroup>('O_NEGATIVE');
  const [matrixData, setMatrixData] = useState<any>(null);
  const [explanation, setExplanation] = useState<string>('');
  const [canDonateTo, setCanDonateTo] = useState<BloodGroup[]>([]);
  const [canReceiveFrom, setCanReceiveFrom] = useState<BloodGroup[]>([]);

  const allGroups: BloodGroup[] = [
    'O_NEGATIVE',
    'O_POSITIVE',
    'A_NEGATIVE',
    'A_POSITIVE',
    'B_NEGATIVE',
    'B_POSITIVE',
    'AB_NEGATIVE',
    'AB_POSITIVE',
  ];

  useEffect(() => {
    // Load compatibility explanation for default / selected group
    const loadGroupData = async () => {
      try {
        const res = await compatibilityApi.checkCompatibility(selectedGroup, selectedGroup);
        if (res && res.explanation) {
          setExplanation(res.explanation);
        }
      } catch (err) {
        // Fallback explanation if offline
      }

      // Compute who this selected group can donate to and receive from
      // Based on scientific red blood cell rules:
      // O- is universal donor
      // AB+ is universal recipient
      const donateMap: Record<string, BloodGroup[]> = {
        O_NEGATIVE: allGroups,
        O_POSITIVE: ['O_POSITIVE', 'A_POSITIVE', 'B_POSITIVE', 'AB_POSITIVE'],
        A_NEGATIVE: ['A_NEGATIVE', 'A_POSITIVE', 'AB_NEGATIVE', 'AB_POSITIVE'],
        A_POSITIVE: ['A_POSITIVE', 'AB_POSITIVE'],
        B_NEGATIVE: ['B_NEGATIVE', 'B_POSITIVE', 'AB_NEGATIVE', 'AB_POSITIVE'],
        B_POSITIVE: ['B_POSITIVE', 'AB_POSITIVE'],
        AB_NEGATIVE: ['AB_NEGATIVE', 'AB_POSITIVE'],
        AB_POSITIVE: ['AB_POSITIVE'],
      };

      const receiveMap: Record<string, BloodGroup[]> = {
        O_NEGATIVE: ['O_NEGATIVE'],
        O_POSITIVE: ['O_NEGATIVE', 'O_POSITIVE'],
        A_NEGATIVE: ['O_NEGATIVE', 'A_NEGATIVE'],
        A_POSITIVE: ['O_NEGATIVE', 'O_POSITIVE', 'A_NEGATIVE', 'A_POSITIVE'],
        B_NEGATIVE: ['O_NEGATIVE', 'B_NEGATIVE'],
        B_POSITIVE: ['O_NEGATIVE', 'O_POSITIVE', 'B_NEGATIVE', 'B_POSITIVE'],
        AB_NEGATIVE: ['O_NEGATIVE', 'A_NEGATIVE', 'B_NEGATIVE', 'AB_NEGATIVE'],
        AB_POSITIVE: allGroups,
      };

      setCanDonateTo(donateMap[selectedGroup] || []);
      setCanReceiveFrom(receiveMap[selectedGroup] || []);
    };

    loadGroupData();
  }, [selectedGroup]);

  // Is donor compatible with recipient?
  const isCompatible = (donor: BloodGroup, recipient: BloodGroup): boolean => {
    const receiveMap: Record<string, BloodGroup[]> = {
      O_NEGATIVE: ['O_NEGATIVE'],
      O_POSITIVE: ['O_NEGATIVE', 'O_POSITIVE'],
      A_NEGATIVE: ['O_NEGATIVE', 'A_NEGATIVE'],
      A_POSITIVE: ['O_NEGATIVE', 'O_POSITIVE', 'A_NEGATIVE', 'A_POSITIVE'],
      B_NEGATIVE: ['O_NEGATIVE', 'B_NEGATIVE'],
      B_POSITIVE: ['O_NEGATIVE', 'O_POSITIVE', 'B_NEGATIVE', 'B_POSITIVE'],
      AB_NEGATIVE: ['O_NEGATIVE', 'A_NEGATIVE', 'B_NEGATIVE', 'AB_NEGATIVE'],
      AB_POSITIVE: allGroups,
    };
    return (receiveMap[recipient] || []).includes(donor);
  };

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 space-y-8">
      {/* Header */}
      <div>
        <span className="text-xs font-bold uppercase tracking-wider text-rose-600 dark:text-rose-400">
          Scientific Matching Guide
        </span>
        <h1 className="text-3xl font-black text-slate-900 dark:text-white tracking-tight mt-1">
          Red Blood Cell Compatibility Matrix
        </h1>
        <p className="text-sm text-slate-500 dark:text-slate-400 mt-2 max-w-2xl leading-relaxed">
          Learn how ABO antigens and Rh(D) factors dictate transfusion compatibility. Click any blood group to inspect
          donor and recipient matching.
        </p>
      </div>

      <MedicalDisclaimer />

      {/* Interactive Blood Group Selector */}
      <div className="bg-white dark:bg-slate-800 rounded-3xl p-6 sm:p-8 border border-slate-200 dark:border-slate-700/80 shadow-sm space-y-6">
        <div>
          <h2 className="text-base font-bold text-slate-900 dark:text-white mb-3">
            Select a Blood Group to Inspect Compatibility
          </h2>
          <div className="grid grid-cols-4 sm:grid-cols-8 gap-2.5">
            {allGroups.map((group) => (
              <button
                key={group}
                onClick={() => setSelectedGroup(group)}
                className={`p-3 rounded-2xl flex flex-col items-center justify-center font-mono transition border ${
                  selectedGroup === group
                    ? 'bg-rose-600 text-white border-rose-600 shadow-md ring-2 ring-rose-500 ring-offset-2'
                    : 'bg-slate-50 dark:bg-slate-700/50 text-slate-800 dark:text-slate-200 border-slate-200 dark:border-slate-600 hover:bg-slate-100 dark:hover:bg-slate-700'
                }`}
              >
                <span className="text-lg font-black">{group.replace('_POSITIVE', '+').replace('_NEGATIVE', '-')}</span>
                <span className="text-[10px] uppercase tracking-wider opacity-80 mt-0.5">
                  {group.includes('NEGATIVE') ? 'Rh -' : 'Rh +'}
                </span>
              </button>
            ))}
          </div>
        </div>

        {/* Selected Group Overview */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6 pt-4 border-t border-slate-100 dark:border-slate-700">
          {/* Can Donate Red Cells To */}
          <div className="bg-slate-50 dark:bg-slate-900/60 rounded-2xl p-5 border border-slate-200 dark:border-slate-700">
            <div className="flex items-center space-x-2 text-rose-600 dark:text-rose-400 font-bold text-sm mb-3">
              <CheckCircle2 className="w-4 h-4" />
              <span>
                As a Donor, {selectedGroup.replace('_POSITIVE', '+').replace('_NEGATIVE', '-')} Can Give RBCs To:
              </span>
            </div>
            <div className="flex flex-wrap gap-2">
              {canDonateTo.map((g) => (
                <BloodGroupBadge key={g} bloodGroup={g} size="md" />
              ))}
            </div>
            <p className="text-xs text-slate-500 dark:text-slate-400 mt-3 leading-relaxed">
              {selectedGroup === 'O_NEGATIVE'
                ? 'Universal RBC Donor: Red cells lack A, B, and Rh antigens, making them safely transfusable to almost all patients in emergency conditions.'
                : `Compatible recipients whose plasma does not contain antibodies against ${selectedGroup.replace('_POSITIVE', '+').replace('_NEGATIVE', '-')}.`}
            </p>
          </div>

          {/* Can Receive Red Cells From */}
          <div className="bg-slate-50 dark:bg-slate-900/60 rounded-2xl p-5 border border-slate-200 dark:border-slate-700">
            <div className="flex items-center space-x-2 text-emerald-600 dark:text-emerald-400 font-bold text-sm mb-3">
              <CheckCircle2 className="w-4 h-4" />
              <span>
                As a Patient, {selectedGroup.replace('_POSITIVE', '+').replace('_NEGATIVE', '-')} Can Receive RBCs From:
              </span>
            </div>
            <div className="flex flex-wrap gap-2">
              {canReceiveFrom.map((g) => (
                <BloodGroupBadge key={g} bloodGroup={g} size="md" />
              ))}
            </div>
            <p className="text-xs text-slate-500 dark:text-slate-400 mt-3 leading-relaxed">
              {selectedGroup === 'AB_POSITIVE'
                ? 'Universal RBC Recipient: Serum lacks anti-A, anti-B, and anti-Rh antibodies, allowing receipt of packed red cells from any blood group.'
                : `Compatible donors whose red cells will not be hemolyzed by the recipient's plasma antibodies.`}
            </p>
          </div>
        </div>
      </div>

      {/* 8x8 Compatibility Grid */}
      <div className="bg-white dark:bg-slate-800 rounded-3xl p-6 sm:p-8 border border-slate-200 dark:border-slate-700/80 shadow-sm space-y-4">
        <div>
          <h2 className="text-lg font-bold text-slate-900 dark:text-white">Complete 8×8 Transfusion Matrix</h2>
          <p className="text-xs text-slate-500 dark:text-slate-400 mt-0.5">
            Horizontal Row = Patient Recipient | Vertical Column = Voluntary Donor
          </p>
        </div>

        <div className="overflow-x-auto">
          <table className="w-full text-xs text-center border-collapse">
            <thead>
              <tr className="border-b border-slate-200 dark:border-slate-700">
                <th className="p-3 text-left font-bold text-slate-500 uppercase tracking-wider text-[11px]">
                  Recipient \ Donor
                </th>
                {allGroups.map((d) => (
                  <th key={d} className="p-3 font-mono font-bold text-slate-700 dark:text-slate-200">
                    {d.replace('_POSITIVE', '+').replace('_NEGATIVE', '-')}
                  </th>
                ))}
              </tr>
            </thead>
            <tbody className="divide-y divide-slate-100 dark:divide-slate-700/60 font-mono">
              {allGroups.map((rec) => (
                <tr
                  key={rec}
                  className={`hover:bg-slate-50 dark:hover:bg-slate-700/30 transition ${
                    selectedGroup === rec ? 'bg-rose-50/50 dark:bg-rose-950/20' : ''
                  }`}
                >
                  <td className="p-3 text-left font-bold text-slate-900 dark:text-white flex items-center space-x-2">
                    <span>{rec.replace('_POSITIVE', '+').replace('_NEGATIVE', '-')}</span>
                    {rec === 'AB_POSITIVE' && (
                      <span className="text-[10px] font-sans font-medium text-emerald-600 dark:text-emerald-400">
                        (Univ. Recipient)
                      </span>
                    )}
                  </td>
                  {allGroups.map((don) => {
                    const compatible = isCompatible(don, rec);
                    return (
                      <td key={don} className="p-3">
                        {compatible ? (
                          <span
                            className="inline-flex items-center justify-center w-7 h-7 rounded-lg bg-emerald-100 dark:bg-emerald-950 text-emerald-700 dark:text-emerald-300 font-bold"
                            title={`${don} can donate to ${rec}`}
                          >
                            ✓
                          </span>
                        ) : (
                          <span
                            className="inline-flex items-center justify-center w-7 h-7 rounded-lg bg-slate-100 dark:bg-slate-800 text-slate-300 dark:text-slate-600"
                            title={`${don} cannot donate to ${rec}`}
                          >
                            —
                          </span>
                        )}
                      </td>
                    );
                  })}
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

      {/* Educational Fact Cards */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="bg-white dark:bg-slate-800 rounded-2xl p-6 border border-slate-200 dark:border-slate-700 space-y-2">
          <BookOpen className="w-5 h-5 text-rose-500" />
          <h3 className="font-bold text-slate-900 dark:text-white text-sm">ABO System Antigens</h3>
          <p className="text-xs text-slate-600 dark:text-slate-300 leading-relaxed">
            The ABO blood group system is defined by presence or absence of A and B carbohydrate antigens on red blood
            cells and naturally occurring antibodies in serum.
          </p>
        </div>

        <div className="bg-white dark:bg-slate-800 rounded-2xl p-6 border border-slate-200 dark:border-slate-700 space-y-2">
          <Sparkles className="w-5 h-5 text-amber-500" />
          <h3 className="font-bold text-slate-900 dark:text-white text-sm">Rh(D) Factor Importance</h3>
          <p className="text-xs text-slate-600 dark:text-slate-300 leading-relaxed">
            Rh-negative individuals develop anti-D antibodies if exposed to Rh-positive blood, posing severe risks in
            subsequent transfusions and pregnancy (hemolytic disease).
          </p>
        </div>

        <div className="bg-white dark:bg-slate-800 rounded-2xl p-6 border border-slate-200 dark:border-slate-700 space-y-2">
          <ShieldCheck className="w-5 h-5 text-emerald-500" />
          <h3 className="font-bold text-slate-900 dark:text-white text-sm">Hospital Cross-Matching</h3>
          <p className="text-xs text-slate-600 dark:text-slate-300 leading-relaxed">
            Even with theoretical compatibility, every hospital blood bank performs mandatory serological major and
            minor cross-matching to prevent atypical antigen reactions.
          </p>
        </div>
      </div>
    </div>
  );
};
